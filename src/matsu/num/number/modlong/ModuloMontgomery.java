/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.7.5
 */
package matsu.num.number.modlong;

import matsu.num.number.MultUtil;

/**
 * {@code long} 型に関する Montgomery modular multiplication を扱う. <br>
 * 除数は 3 以上の奇数である.
 * 
 * @author Matsuura Y.
 */
final class ModuloMontgomery extends SkeletalModuloLong {
    /*
     * 事前準備: Montgomery multiplication (モンゴメリ乗算)
     * 
     * R は 2 の累乗数である. (int なら 2^32, long なら 2^64 とする)
     * m を 3 以上の奇数かつ m < R とする. m と R は互いに素である.
     * m' を, m*m' mod R = -1 を満たす, 0 <= m' < R である整数とする.
     * 
     * a を, 0 <= a < m*R を満たす整数とし, a のモンゴメリリダクション MR(a) を次で定義する.
     * MR(a) = (a * R^(-1)) mod m
     * ここで, 0 <= MR(a) < m であり, R^(-1) は R * R^(-1) = 1 mod m を満たす整数である.
     * MR(a) は次のアルゴリズムで計算できる.
     * 
     * 1. t = (a + m*((a * m') mod R)) / R
     * 2. MR(a) = t or t - m
     * (初段の / R は通常の意味の除算である (被除数は R の倍数となっている).)
     * 
     * モンゴメリリダクションの逆演算をモンゴメリ変換といい, a のモンゴメリ変換 M(a) は次で計算される.
     * M(a) = MR(a * R^2)
     * 0 <= a < R のモンゴメリ変換を扱いたい場合, R_2 = R^2 mod m を計算しておき,
     * M(a) = MR(a * R_2) とすればよい.
     */

    /*
     * このクラスの実装方針: 3 以上の奇数を法とする剰余計算を, Montgomery multiplication (モンゴメリ乗算) で行う.
     * 
     * 単純な剰余: a mod m = MR(M(a))
     * 
     * 2数の乗算剰余: ab mod m = MR(M(a) * b)
     * 
     * 3数以上の乗算剰余:
     * M(ab) = MR(M(a) * M(b)) という性質を使い,
     * abc... の積を求めるとき, a, b, c, ... を M(a), M(b), M(c), ... に変換後,
     * M(abc...) = MR(M(a) * MR(M(b) * MR(M(c) * ...)))
     * と計算し, MR(M(abc...)) を得る.
     */

    /*
     * R = 2^64 とする.
     */

    /** 除数 m */
    private final long divisor;

    /** R_2 = R^2 mod m */
    private final long R_2;

    /**
     * m*m' mod R = -1 を満たす, 0 <= m' < R である整数.
     * <br>
     * 符号なしで解釈する.
     */
    private final long m_prime;

    /** M(1) の値 */
    private final long montg_1;

    private final DividendPositivizer modPositivize;

    /**
     * 与えた正整数を法としたモジュロ演算を構築する.
     * 
     * <p>
     * 引数は3以上の奇数でなければならない. <br>
     * 引数のバリデーションは行われていないので,
     * 呼び出しもとでチェックすること.
     * </p>
     * 
     * @param divisor 除数
     */
    ModuloMontgomery(long divisor) {
        super();
        assert divisor >= 3L && (divisor & 1L) == 1L;

        this.divisor = divisor;

        // この2個は, 内部に重複する部分がある.
        // ただし, コストは大きくないので, 共通化しなくてもいいかも知れない.
        this.modPositivize = new DividendPositivizer(divisor);
        this.R_2 = DividendShifterUtil.computeLong(1L, 128, divisor);

        // m' は符号なしで解釈するので, 負符号を付けて良い.
        this.m_prime = -ModPow2InverseUtil.invModR(divisor);
        this.montg_1 = toMontg(1);
    }

    @Override
    public long divisor() {
        return divisor;
    }

    @Override
    public long mod(long n) {

        // n mod m = MR(M(n))
        // 剰余演算 (%) よりパフォーマンスが良いかどうかについては, 何も保証しない.
        n = modPositivize.apply(n);

        return n < divisor
                ? n
                : reduceMontg(toMontg(n));
    }

    @Override
    public long modpr(long a, long b) {

        a = modPositivize.apply(a);
        b = modPositivize.apply(b);

        // ab mod m = MR(M(a) * b)
        return reduceMontg(toMontg(a), b);
    }

    @Override
    public long modpr(long... x) {
        switch (x.length) {
            case 0:
                return 1L;
            case 1:
                return mod(x[0]);
            case 2:
                return modpr(x[0], x[1]);
            default:
                // ブロック外で処理
        }

        // サイズ3以上

        //xを正に変換, x -> M(x)
        int len = x.length;
        x = x.clone();
        for (int i = 0; i < len; i++) {
            x[i] = toMontg(modPositivize.apply(x[i]));
        }

        // 結合法則を利用して, 4系列に分割
        // mcの単位元で初期化
        long v0 = montg_1;
        long v1 = v0;
        long v2 = v0;
        long v3 = v0;
        int i;
        for (i = 0; i < len - 3; i += 4) {
            v0 = reduceMontg(v0, x[i]);
            v1 = reduceMontg(v1, x[i + 1]);
            v2 = reduceMontg(v2, x[i + 2]);
            v3 = reduceMontg(v3, x[i + 3]);
        }
        for (; i < len; i++) {
            v0 = reduceMontg(v0, x[i]);
        }

        return reduceMontg(reduceMontg(reduceMontg(v0, v1), reduceMontg(v2, v3)));
    }

    @Override
    long modpowConcrete(long x, long k) {
        x = modPositivize.apply(x);
        if (k <= Integer.MAX_VALUE) {
            switch ((int) k) {
                case 0:
                    return 1;
                case 1:
                    return mod(x);
                case 2:
                    return modpr(x, x);
                default:
                    // ブロック外で処理
            }
        }

        // 指数3以上
        long montg_out = montg_1;
        long montg_xPow = toMontg(x);
        while (k > 0) {
            if ((k & 1L) == 1L) {
                montg_out = reduceMontg(montg_out, montg_xPow);
            }

            k >>= 1;
            montg_xPow = reduceMontg(montg_xPow, montg_xPow);
        }

        return reduceMontg(montg_out);
    }

    /**
     * a*b に対する Montgomery reduction を計算する. <br>
     * a,bは符号なし64bitで扱う. <br>
     * a*b は 2^(64) * m 未満でなければならない
     * (mは除数).
     * 
     * @param a a
     * @param b b
     * @return MR(ab)
     */
    private long reduceMontg(long a, long b) {
        long[] ab = MultUtil.unsignedMultiplyFullLong(a, b);
        long high_ab = ab[0];
        long low_ab = ab[1];
        long Tnn_high = MultUtil.unsignedMultiplyHighLong(low_ab * m_prime, divisor);
        if (low_ab != 0) {
            Tnn_high++;
        }

        long t = high_ab + Tnn_high;
        if (t < 0L || t >= divisor) {
            t -= divisor;
        }

        return t;
    }

    /**
     * a の Montgomery 変換を計算する. <br>
     * aは符号なし64bitで扱う.
     * 
     * @param a a
     * @return M(a)
     */
    private long toMontg(long a) {
        return reduceMontg(a, R_2);
    }

    /**
     * a の Montgomery reduction を計算する. <br>
     * aは符号なし64bitで扱う.
     * 
     * @param a a
     * @return MR(a)
     */
    private long reduceMontg(long a) {
        if (a == 0L) {
            return 0L;
        }

        // 1 <= a < 2^(32)より, 
        // モンゴメリリダクションでは, (TN' mod R)*N の上位32bitに1を加えればよい.
        return MultUtil.unsignedMultiplyHighLong(a * m_prime, divisor) + 1L;
    }
}
