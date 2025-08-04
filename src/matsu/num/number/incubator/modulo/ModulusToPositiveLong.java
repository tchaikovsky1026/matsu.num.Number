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
final class ModulusToPositiveLong {

    /**
     * 除数
     */
    private final long m;

    /**
     * 2^63 - (2^63 % N)
     */
    private final long k;

    /**
     * @param m 除数, 1以上
     */
    ModulusToPositiveLong(long m) {
        super();
        if (m <= 0) {
            throw new IllegalArgumentException();
        }
        this.m = m;

        /*
         * もっとも2^63に近い, (mod m = 0)の値
         * 2^63 の場合もあり得る.
         */
        this.k = (1L << 63) - ModuloShifting.computeLongOptimized(1L, 63, m);

        assert this.k == Integer.MIN_VALUE ||
                this.k % m == 0;
    }

    long divisor() {
        return m;
    }

    /**
     * 符号付き整数xを, mod m を維持したまま0以上に変換する.
     * 
     * @param x x
     * @return x + const * m
     */
    long toPositive(long x) {
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
