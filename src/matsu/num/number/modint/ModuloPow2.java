/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.7.1
 */
package matsu.num.number.modint;

import matsu.num.number.ModuloInt;

/**
 * 2の累乗を除数とする, {@link ModuloInt}. <br>
 * {@code int} で扱える範囲である, 2^1 から 2^30 の範囲を扱う.
 * 
 * @author Matsuura Y.
 */
final class ModuloPow2 extends SkeletalModuloInt {

    private final int divisor;

    /** mod 2^k を計算するためのマスク. */
    /*
     * x mod 2^k は, x & (2^k-1) に等しい.
     * x が負であってもよい.
     */
    private final int bitMask;

    /**
     * 指数 k を与えて, 2^k を法としたモジュロ演算を構築する.
     * 
     * <p>
     * {@literal 1 <= k <= 30} でなければならない. <br>
     * 引数のバリデーションは行われていないので,
     * 呼び出しもとでチェックすること.
     * </p>
     * 
     * @param exponent 指数
     */
    ModuloPow2(int exponent) {
        super();
        assert 1 <= exponent && exponent <= 30;

        this.divisor = 1 << exponent;
        this.bitMask = this.divisor - 1;
    }

    @Override
    public int divisor() {
        return this.divisor;
    }

    @Override
    public int mod(int x) {
        return x & bitMask;
    }

    @Override
    public int modpr(int x, int y) {
        // 下位 bit の抽出目的ではオーバーフローしても良い.
        // 負でもよい.
        return (x * y) & bitMask;
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

        // 以下は, サイズ3以上の処理である.

        // 積の下位 bit の抽出目的ではオーバーフローしても良い.
        // 負でもよい.
        int len = x.length;

        // 結合法則を利用して, 4系列に分割
        // mcの単位元で初期化
        int v0 = 1;
        int v1 = 1;
        int v2 = 1;
        int v3 = 1;
        int i;
        for (i = 0; i < len - 3; i += 4) {
            v0 *= x[i];
            v1 *= x[i + 1];
            v2 *= x[i + 2];
            v3 *= x[i + 3];
        }
        for (; i < len; i++) {
            v0 *= x[i];
        }

        return ((v0 * v1) * (v2 * v3)) & bitMask;
    }

    @Override
    int modpowConcrete(int x, int k) {
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

        // 以下は, 指数3以上の処理である.

        /*
         * 指数 k を bit 解析し, x^k を x^(2^n) の積として表現
         * x^(2^(n+1)) = (x^(2^n))^2 の関係を使い, 逐次 x^(2^n) (mod m) の値を計算.
         */
        int out = 1;
        int xPow = x;
        while (k > 0) {
            if ((k & 1) == 1) {
                out *= xPow;
            }

            k >>= 1;
            xPow = xPow * xPow;
        }

        return out & bitMask;
    }
}
