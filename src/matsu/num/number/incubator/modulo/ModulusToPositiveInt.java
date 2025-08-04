/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.4
 */
package matsu.num.number.incubator.modulo;

/**
 * 与えた数を, mod を不変のまま0以上に変換する.
 * 
 * @author Matsuura Y.
 */
final class ModulusToPositiveInt {

    /**
     * 除数
     */
    private final int m;

    /**
     * 2^31 - (2^31 % N)
     */
    private final int k;

    /**
     * @param m 除数, 1以上
     */
    ModulusToPositiveInt(int m) {
        super();
        if (m <= 0) {
            throw new IllegalArgumentException();
        }
        this.m = m;

        /*
         * もっとも2^31に近い, (mod m = 0)の値
         * 2^31 の場合もあり得る.
         */
        this.k = (1 << 31) - ModuloShifting.computeIntOptimized(1, 31, m);

        assert this.k == Integer.MIN_VALUE ||
                this.k % m == 0;
    }

    int divisor() {
        return m;
    }

    /**
     * 符号付き整数xを, mod m を維持したまま0以上に変換する.
     * 
     * @param x x
     * @return x + const * m
     */
    int toPositive(int x) {
        if (x >= 0) {
            return x;
        }

        // 負の場合, 2回加えれば必ず0以上になる

        x += k;
        if (x >= 0) {
            return x;
        }

        return x + k;
    }
}
