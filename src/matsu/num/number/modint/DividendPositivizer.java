/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.7.4
 */
package matsu.num.number.modint;

/**
 * {@code int} 型の符号付き整数を, mod を不変のまま 0 以上に変換するユーティリティ.
 * 
 * @author Matsuura Y.
 */
final class DividendPositivizer {

    /** 除数 */
    private final int divisor;

    /**
     * 2^31 以下で最も大きい (mod m = 0) の値を表す. <br>
     * k = 2^31 - (2^31 % N) であり, 最小で 2^30 + 1, 最大で 2^31 である.
     */
    private final int k;

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
    DividendPositivizer(int divisor) {
        super();
        if (divisor <= 0) {
            throw new IllegalArgumentException();
        }
        this.divisor = divisor;

        k = (1 << 31) - DividendShifterUtil.computeInt(1, 31, divisor);

        assert k == Integer.MIN_VALUE || k % divisor == 0;
    }

    /**
     * 除数を返す.
     * 
     * @return 除数
     */
    int divisor() {
        return divisor;
    }

    /**
     * 符号付き整数xを, mod m を維持したまま0以上に変換する.
     * 
     * @param x x
     * @return x + const * m
     */
    int apply(int x) {
        if (x >= 0) {
            return x;
        }

        /*
         * x が 負の場合,
         * 2^30 + 1 <= k <= 2^31 より, k を 2 回加えれば必ず 0 以上になる.
         * 
         * (-k) は 符号付き整数として正確である.
         * 
         * k << 1 は k の 2 倍を表す.
         * (k << 1) が実行される条件では, (k << 1) は符号なし整数として正確に k の 2 倍である.
         */
        return x >= (-k)
                ? x + k
                : x + (k << 1);
    }
}
