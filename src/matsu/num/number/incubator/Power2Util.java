/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.7
 */
package matsu.num.number.incubator;

/**
 * 2の累乗に関係するユーティリティ.
 * 
 * <p>
 * インキュベータであるが, 公開できる可能性がある.
 * </p>
 * 
 * @author Matsuura Y.
 */
public final class Power2Util {

    private Power2Util() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /**
     * {@code int} 型の正の整数が 2 の累乗かどうかを検証し, 2 の累乗ならばその指数を計算する. <br>
     * 2 の累乗ならその指数を, 累乗でないなら -1 を返す.
     * 
     * <p>
     * 検証する値は正の数でなければならない.
     * </p>
     * 
     * @param n <i>n</i>, 指数計算をする値
     * @return <i>n</i> が 2 の累乗ならその指数, 累乗でないなら -1
     * @throws IllegalArgumentException <i>n</i> が0以下の場合
     */
    public static int exponentOf2(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("illegal: n <= 0");
        }

        int exponent = Integer.numberOfTrailingZeros(n);

        return n - (1 << exponent) == 0
                ? exponent
                : -1;
    }

    /**
     * {@code long} 型の正の整数が 2 の累乗かどうかを検証し, 2 の累乗ならばその指数を計算する. <br>
     * nが 2 の累乗ならその指数を, 累乗でないなら -1 を返す.
     * 
     * <p>
     * 検証する値は正の数でなければならない.
     * </p>
     * 
     * @param n <i>n</i>, 指数計算をする値
     * @return <i>n</i> が 2 の累乗ならその指数, 累乗でないなら -1
     * @throws IllegalArgumentException <i>n</i> が0以下の場合
     */
    public static int exponentOf2(long n) {
        if (n <= 0L) {
            throw new IllegalArgumentException("illegal: n <= 0");
        }

        int exponent = Long.numberOfTrailingZeros(n);

        return n - (1L << exponent) == 0
                ? exponent
                : -1;
    }
}
