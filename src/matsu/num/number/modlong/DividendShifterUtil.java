/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.7.5
 */
package matsu.num.number.modlong;

/**
 * {@literal (N << shift) % m} を計算することに関するユーティリティ. <br>
 * すべて符号有り整数として解釈される. <br>
 * shiftによるオーバーフローを回避するように実装される.
 * 
 * @author Matsuura Y.
 */
final class DividendShifterUtil {

    private DividendShifterUtil() {
        //インスタンス化不可
        throw new AssertionError();
    }

    /**
     * {@code int} 型の N (0以上), m (1以上) について, {@literal (N << shift) % m}
     * を計算する.
     * 
     * <p>
     * 引数はバリデーションされない. <br>
     * {@code N}, {@code shift} は 0 以上,
     * {@code m} は 1 以上でなければならない.
     * </p>
     *
     * @param n N
     * @param shift shift
     * @param m m
     * @return {@literal (N << shift) % m}
     */
    static long computeLong(long n, int shift, long m) {
        assert n >= 0;
        assert m >= 1;
        assert shift >= 0;

        // n を正規化
        n %= m;

        /*
         * 除数が 2^62 以下の場合,
         * "シフトにより 62-bit (63ケタ) までシフトして剰余をとる" を繰り返す.
         * 
         * 除数が2^62 以上の場合, これは63bitなので,
         * "シフトにより64bitまでシフトし, 除数を引く"を繰り返す.
         */
        if (m <= (1L << 62)) {
            // m <= 2^62
            while (shift > 0) {
                int currentShift = Math.min(shift, Long.numberOfLeadingZeros(n) - 1);
                n <<= currentShift;
                n %= m;
                shift -= currentShift;
            }
            return n;
        } else {
            // m > 2^62

            for (; shift > 0; shift--) {
                n <<= 1;

                // 符号なし整数の意味での n >= m の判定: n < 0 || n >= m
                // 左シフトは2倍なので, 1回の判定で必ず正規化される.
                if (n < 0 || n >= m) {
                    n -= m;
                }
            }
            return n;
        }
    }
}
