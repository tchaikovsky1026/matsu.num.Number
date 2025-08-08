/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.7
 */
package matsu.num.number.modulo;

/**
 * {@code long} 型の符号付き整数を, mod を不変のまま0以上に変換する.
 * 
 * @author Matsuura Y.
 */
final class ModPositivizeLong {

    /**
     * 除数
     */
    private final long divisor;

    /**
     * 2^63 - (2^63 % N), 最大で2^63
     */
    private final long k;

    /**
     * -k を表す.
     */
    private final long negK;

    /**
     * kの2倍を表す.
     * オーバーフローしても良い.
     */
    private final long doubleK;

    /**
     * 除数を与えてインスタンスを構築する. <br>
     * 除数は1以上でなければならない.
     * 
     * <p>
     * 引数のバリデーションはされていないので,
     * 呼び出しもとでチェックすること.
     * </p>
     * 
     * @param divisor 除数, 1以上
     */
    ModPositivizeLong(long divisor) {
        super();
        if (divisor <= 0) {
            throw new IllegalArgumentException();
        }
        this.divisor = divisor;

        /*
         * もっとも2^63に近い, (mod m = 0)の値
         * 2^63 の場合もあり得る.
         */
        this.k = (1L << 63) - ModuloShifting.computeLong(1L, 63, divisor);
        this.doubleK = k << 1;
        this.negK = -k;

        assert this.k == Integer.MIN_VALUE ||
                this.k % divisor == 0;
    }

    long divisor() {
        return divisor;
    }

    /**
     * 符号付き整数xを, mod m を維持したまま0以上に変換する.
     * 
     * @param x x
     * @return x + const * m
     */
    long apply(long x) {
        if (x >= 0) {
            return x;
        }

        // 負の場合, 2回加えれば必ず0以上になる
        return x >= negK
                ? x + k
                : x + doubleK;
    }
}
