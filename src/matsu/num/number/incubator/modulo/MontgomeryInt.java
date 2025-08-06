/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.6
 */
package matsu.num.number.incubator.modulo;

import matsu.num.number.NumberUtil;

/**
 * {@code int} 型に関する Montgomery modular multiplication を扱う.
 * 
 * @author Matsuura Y.
 */
final class MontgomeryInt implements ModulusInt {

    private final int divisor;
    private final int r2;

    /**
     * divisor の 2^(32)を法とするモジュロ逆数. <br>
     * 符号なしで解釈する.
     */
    private final int n_prime;

    private final int mc_identity;

    private final ModulusToPositiveInt modulusToPositiveInt;

    /**
     * 除数は正の奇数でなければならない.
     * 
     * @param divisor 除数
     */
    private MontgomeryInt(int divisor) {
        super();
        assert divisor >= 1 && (divisor & 1) == 1;

        this.divisor = divisor;

        // この2個は, 内部に重複する部分がある.
        // ただし, コストは大きくないので, 共通化しなくてもいいかも知れない.
        this.modulusToPositiveInt = new ModulusToPositiveInt(divisor);
        this.r2 = ModuloShifting.computeIntOptimized(1, 64, divisor);

        this.n_prime = -InverseModR.invModR(divisor);
        this.mc_identity = toMong(1);
    }

    @Override
    public int divisor() {
        return this.divisor;
    }

    /**
     * n mod m を返す
     * (m は除数).
     * 
     * @param n
     * @return n mod m
     */
    @Override
    public int mod(int n) {
        // n -> mod m　を維持して正に変換
        // モンゴメリ変換とリダクションでmodに戻す.

        n = this.modulusToPositiveInt.toPositive(n);

        return reduceMong(toMong(n));
    }

    /**
     * a*b mod m を返す
     * (m は除数).
     * 
     * @param a a
     * @param b b
     * @return a*b mod m
     */
    @Override
    public int modpr(int a, int b) {

        // a,bは2^31-1以下にマップされる
        a = this.modulusToPositiveInt.toPositive(a);
        b = this.modulusToPositiveInt.toPositive(b);

        /*
         * 以下の等式は mod m として見る.
         * a*b = aR b R^(-1) = mr(mong(a)*b)
         */
        // (mod m) ab = ()

        return reduceMong(toMong(a), b);
    }

    /**
     * x1*x2*... mod m を返す
     * (m は除数).
     * 
     * <p>
     * lengthが0の場合は1が返る.
     * </p>
     * 
     * @param x x
     * @return x1*x2*... mod m
     * @throws NullPointerException 引数がnullの場合
     */
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
        x = x.clone();
        int len = x.length;

        //xを正に変換してモンゴメリ変換
        for (int i = 0; i < len; i++) {
            x[i] = toMong(this.modulusToPositiveInt.toPositive(x[i]));
        }

        // 結合法則を利用して, 4系列に分割
        // mcの単位元で初期化
        int v0 = mc_identity;
        int v1 = v0;
        int v2 = v0;
        int v3 = v0;
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

    /**
     * x^k mod m を返す
     * (m は除数).
     * 
     * <p>
     * 指数は 0 以上でなければならない. <br>
     * x = 0, k = 0 の場合は1が返る.
     * </p>
     * 
     * @param x x
     * @param k k
     * @return x^k mod m
     * @throws IllegalArgumentException 指数が0未満の場合
     */
    @Override
    public int modpow(int x, int k) {
        if (k < 0) {
            throw new IllegalArgumentException("illegal: exponent k is negative: k = " + k);
        }

        x = this.modulusToPositiveInt.toPositive(x);
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
        int mong_out = mc_identity;
        int mong_xPow = toMong(x);
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
     * a,bは符号なし32bitで扱う. <br>
     * a*b は 2^(32) * m 未満でなければならない
     * (mは除数).
     * 
     * @param a a
     * @param b b
     * @return mr(ab)
     */
    private int reduceMong(int a, int b) {
        long ab = NumberUtil.unsignedMultiplyFull(a, b);
        int low_ab = (int) ab;
        int Tnn_high = NumberUtil.unsignedMultiplyHigh(low_ab * n_prime, divisor);
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
    private int toMong(int a) {
        return reduceMong(a, r2);
    }

    /**
     * a の Montgomery reduction を計算する. <br>
     * aは符号なし32bitで扱う.
     * 
     * @param a a
     * @return mr(a)
     */
    private int reduceMong(int a) {
        if (a == 0) {
            return 0;
        }

        // 1 <= a < 2^(32)より, 
        // モンゴメリリダクションでは, (TN' mod R)*N の上位32bitに1を加えればよい.
        return NumberUtil.unsignedMultiplyHigh(a * n_prime, divisor) + 1;
    }

    /**
     * 与えた正整数を法としたモジュロ演算を構築する.
     * 
     * <p>
     * 引数は1以上の奇数でなければならない. <br>
     * 引数のバリデーションは行われていないので,
     * 呼び出しもとでチェックすること.
     * </p>
     * 
     * @param m 除数
     */
    static ModulusInt of(int divisor) {
        assert divisor >= 1 && (divisor & 1) == 1;

        return new MontgomeryInt(divisor);
    }
}
