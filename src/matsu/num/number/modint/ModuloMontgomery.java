/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.7.10
 */
package matsu.num.number.modint;

import matsu.num.number.MultUtil;

/**
 * {@code int} 型に関する Montgomery modular multiplication を扱う. <br>
 * 除数は 3 以上の奇数である.
 * 
 * @author Matsuura Y.
 */
final class ModuloMontgomery extends SkeletalModuloInt {

    /** 除数 m */
    private final int divisor;

    /** R_2 = R^2 mod m */
    private final int R_2;

    /**
     * m*m' mod R = -1 を満たす, 0 <= m' < R である整数.
     * <br>
     * 符号なしで解釈する.
     */
    private final int m_prime;

    /** M(1) の値 */
    private final int montg_1;

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
    ModuloMontgomery(int divisor) {
        super();
        assert divisor >= 3 && (divisor & 1) == 1;

        this.divisor = divisor;

        // この2個は, 内部に重複する部分がある.
        // ただし, コストは大きくないので, 共通化しなくてもいいかも知れない.
        this.modPositivize = new DividendPositivizer(divisor);
        this.R_2 = DividendShifterUtil.computeInt(1, 64, divisor);

        // m' は符号なしで解釈するので, 負符号を付けて良い.
        this.m_prime = -ModPow2InverseUtil.invModR(divisor);
        this.montg_1 = toMontg(1);
    }

    @Override
    public int divisor() {
        return divisor;
    }

    @Override
    public int mod(int n) {

        // n mod m = MR(M(n))
        // 剰余演算 (%) よりパフォーマンスが良いかどうかについては, 何も保証しない.
        n = modPositivize.apply(n);

        return n < divisor
                ? n
                : reduceMontg(toMontg(n));
    }

    @Override
    public int modpr(int a, int b) {

        a = modPositivize.apply(a);
        b = modPositivize.apply(b);

        // ab mod m = MR(M(a) * b)
        return reduceMontg(toMontg(a), b);
    }

    @Override
    public int modpr(int... x) {
        switch (x.length) {
            case 0:
                return 1;
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
        // M(1)で初期化
        int v0 = montg_1;
        int v1 = v0;
        int v2 = v0;
        int v3 = v0;
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
    int modpowConcrete(int x, int k) {
        x = modPositivize.apply(x);
        switch (k) {
            case 0:
                return 1;
            case 1:
                return mod(x);
            case 2:
                return modpr(x, x);
            default:
                // ブロック外で処理
        }

        // 指数3以上
        int mong_out = montg_1;
        int mong_xPow = toMontg(x);
        while (k > 0) {
            if ((k & 1) == 1) {
                mong_out = reduceMontg(mong_out, mong_xPow);
            }

            k >>= 1;
            mong_xPow = reduceMontg(mong_xPow, mong_xPow);
        }

        return reduceMontg(mong_out);
    }

    /**
     * a*b に対する Montgomery reduction を計算する. <br>
     * a,bは符号なし32bitで扱う. <br>
     * a*b は 2^(32) * m 未満でなければならない
     * (mは除数).
     * 
     * @param a a
     * @param b b
     * @return mr(ab)
     */
    private int reduceMontg(int a, int b) {
        long ab = MultUtil.unsignedMultiplyFull(a, b);
        int low_ab = (int) ab;
        int Tnn_high = MultUtil.unsignedMultiplyHigh(low_ab * m_prime, divisor);
        if (low_ab != 0) {
            Tnn_high++;
        }

        int t = (int) (ab >> 32) + Tnn_high;
        if (t < 0 || t >= divisor) {
            t -= divisor;
        }

        return t;
    }

    /**
     * a の Montgomery 変換を計算する. <br>
     * aは符号なし32bitで扱う.
     * 
     * @param a a
     * @return mong(a)
     */
    private int toMontg(int a) {
        return reduceMontg(a, R_2);
    }

    /**
     * a の Montgomery reduction を計算する. <br>
     * aは符号なし32bitで扱う.
     * 
     * @param a a
     * @return mr(a)
     */
    private int reduceMontg(int a) {
        if (a == 0) {
            return 0;
        }

        // 1 <= a < 2^(32)より, 
        // モンゴメリリダクションでは, (TN' mod R)*N の上位32bitに1を加えればよい.
        return MultUtil.unsignedMultiplyHigh(a * m_prime, divisor) + 1;
    }
}
