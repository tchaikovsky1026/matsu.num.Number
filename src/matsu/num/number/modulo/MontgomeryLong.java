/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.8
 */
package matsu.num.number.modulo;

import matsu.num.number.MultiplicationUtil;

/**
 * {@code long} 型に関する Montgomery modular multiplication を扱う. <br>
 * 除数は 3 以上の奇数である.
 * 
 * @author Matsuura Y.
 */
final class MontgomeryLong extends SkeletalModulusLong {

    private final long divisor;
    private final long r2;

    /**
     * divisor の 2^(64)を法とするモジュロ逆数. <br>
     * 符号なしで解釈する.
     */
    private final long n_prime;

    private final long mc_identity;

    private final ModPositivizeLong modPositivize;

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
    MontgomeryLong(long divisor) {
        super();
        assert divisor >= 3L && (divisor & 1L) == 1L;

        this.divisor = divisor;

        // この2個は, 内部に重複する部分がある.
        // ただし, コストは大きくないので, 共通化しなくてもいいかも知れない.
        this.modPositivize = new ModPositivizeLong(divisor);
        this.r2 = ModuloShifting.computeLong(1L, 128, divisor);

        this.n_prime = -InverseModPow2.invModR(divisor);
        this.mc_identity = toMong(1);
    }

    @Override
    public long divisor() {
        return this.divisor;
    }

    @Override
    public long mod(long n) {

        // n -> mod m　を維持して正に変換
        // モンゴメリ変換とリダクションでmodに戻す.
        n = this.modPositivize.apply(n);

        return n < this.divisor
                ? n
                : reduceMong(toMong(n));
    }

    @Override
    public long modpr(long a, long b) {

        // a,bは2^31-1以下にマップされる
        a = this.modPositivize.apply(a);
        b = this.modPositivize.apply(b);

        /*
         * 以下の等式は mod m として見る.
         * a*b = aR b R^(-1) = mr(mong(a)*b)
         */
        // (mod m) ab = ()

        return reduceMong(toMong(a), b);
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
        x = x.clone();
        int len = x.length;

        //xを正に変換してモンゴメリ変換
        for (int i = 0; i < len; i++) {
            x[i] = toMong(this.modPositivize.apply(x[i]));
        }

        // 結合法則を利用して, 4系列に分割
        // mcの単位元で初期化
        long v0 = mc_identity;
        long v1 = v0;
        long v2 = v0;
        long v3 = v0;
        int i;
        for (i = 0; i < len - 3; i += 4) {
            v0 = reduceMong(v0, x[i]);
            v1 = reduceMong(v1, x[i + 1]);
            v2 = reduceMong(v2, x[i + 2]);
            v3 = reduceMong(v3, x[i + 3]);
        }
        for (; i < len; i++) {
            v0 = reduceMong(v0, x[i]);
        }

        return reduceMong(reduceMong(reduceMong(v0, v1), reduceMong(v2, v3)));
    }

    @Override
    public long modpow(long x, int k) {
        if (k < 0) {
            throw new IllegalArgumentException("illegal: exponent k is negative: k = " + k);
        }

        x = this.modPositivize.apply(x);
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
        long mong_out = mc_identity;
        long mong_xPow = toMong(x);
        while (k > 0) {
            if ((k & 1) == 1) {
                mong_out = reduceMong(mong_out, mong_xPow);
            }

            k >>= 1;
            mong_xPow = reduceMong(mong_xPow, mong_xPow);
        }

        return reduceMong(mong_out);
    }

    /**
     * a*b に対する Montgomery reduction を計算する. <br>
     * a,bは符号なし64bitで扱う. <br>
     * a*b は 2^(64) * m 未満でなければならない
     * (mは除数).
     * 
     * @param a a
     * @param b b
     * @return mr(ab)
     */
    private long reduceMong(long a, long b) {
        long[] ab = MultiplicationUtil.unsignedMultiplyFullLong(a, b);
        long high_ab = ab[0];
        long low_ab = ab[1];
        long Tnn_high = MultiplicationUtil.unsignedMultiplyHighLong(low_ab * n_prime, divisor);
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
     * @return mong(a)
     */
    private long toMong(long a) {
        return reduceMong(a, r2);
    }

    /**
     * a の Montgomery reduction を計算する. <br>
     * aは符号なし32bitで扱う.
     * 
     * @param a a
     * @return mr(a)
     */
    private long reduceMong(long a) {
        if (a == 0) {
            return 0;
        }

        // 1 <= a < 2^(32)より, 
        // モンゴメリリダクションでは, (TN' mod R)*N の上位32bitに1を加えればよい.
        return MultiplicationUtil.unsignedMultiplyHighLong(a * n_prime, divisor) + 1L;
    }
}
