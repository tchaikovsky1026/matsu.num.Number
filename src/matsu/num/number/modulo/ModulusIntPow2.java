/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.6
 */
package matsu.num.number.modulo;

/**
 * 2の累乗を除数とする, {@link ModulusInt}.
 * 
 * @author Matsuura Y.
 */
final class ModulusIntPow2 extends SkeletalModulusInt {

    private final int divisor;
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
    ModulusIntPow2(int exponent) {
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
        // 注: 2の累乗を法としたmodは, 符号ありなしで同一である.
        return (x * y) & bitMask;
    }

    @Override
    public int modpr(int... x) {
        // 注: 2の累乗を法としたmodは, 符号ありなしで同一である.

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
    public int modpow(int x, int k) {
        if (k < 0) {
            throw new IllegalArgumentException("illegal: exponent k is negative: k = " + k);
        }

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
        int out = 1;
        int xPow = x;
        while (k > 0) {
            if ((k & 1) == 1) {
                out *= xPow;
            }

            k >>= 1;
            xPow *= xPow;
        }

        return out & bitMask;
    }
}
