/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.10
 */
package matsu.num.number.modulo;

/**
 * 2の累乗を除数とする, {@link ModuloLong}.
 * 
 * @author Matsuura Y.
 */
final class ModuloLongPow2 extends SkeletalModuloLong {

    private final long divisor;
    private final long bitMask;

    /**
     * 指数 k を与えて, 2^k を法としたモジュロ演算を構築する.
     * 
     * <p>
     * {@literal 1 <= k <= 62} でなければならない. <br>
     * 引数のバリデーションは行われていないので,
     * 呼び出しもとでチェックすること.
     * </p>
     * 
     * @param exponent 指数
     */
    ModuloLongPow2(int exponent) {
        super();
        assert 1 <= exponent && exponent <= 62;

        this.divisor = 1L << exponent;
        this.bitMask = this.divisor - 1;
    }

    @Override
    public long divisor() {
        return this.divisor;
    }

    @Override
    public long mod(long x) {
        return x & bitMask;
    }

    @Override
    public long modpr(long x, long y) {
        // 注: 2の累乗を法としたmodは, 符号ありなしで同一である.
        return (x * y) & bitMask;
    }

    @Override
    public long modpr(long... x) {
        // 注: 2の累乗を法としたmodは, 符号ありなしで同一である.

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

        // 結合法則を利用して, 4系列に分割
        // mcの単位元で初期化
        long v0 = 1;
        long v1 = 1;
        long v2 = 1;
        long v3 = 1;
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
    public long modpow(long x, long k) {
        if (k < 0) {
            throw new IllegalArgumentException("illegal: exponent k is negative: k = " + k);
        }

        if (k <= Integer.MAX_VALUE) {
            switch ((int) k) {
                case 0:
                    return 1L;
                case 1:
                    return mod(x);
                case 2:
                    return modpr(x, x);
                default:
                    // ブロック外で処理
            }
        }

        // 指数3以上
        long out = 1L;
        long xPow = x;
        while (k > 0L) {
            if ((k & 1L) == 1L) {
                out *= xPow;
            }

            k >>= 1;
            xPow *= xPow;
        }

        return out & bitMask;
    }
}
