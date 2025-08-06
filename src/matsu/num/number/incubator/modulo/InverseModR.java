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

/**
 * 整数の, 2^(bit) に対するモジュロ逆元を計算することに関するプロトタイプ. <br>
 * 2^(32), 2^(64) に対応し, 符号なし整数として扱う.
 * 
 * @author Matsuura Y.
 */
final class InverseModR {

    private InverseModR() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /**
     * 符号なし int について, 2^(32) を法とした逆元を計算する.
     * 
     * <p>
     * n は奇数でなければならない.
     * </p>
     * 
     * @param n 符号なし整数
     * @return n^(-1) (mod 2^(32))
     */
    static int invModR(int n) {
        assert (n & 1) == 1;

        int r = 1;
        int currentRMask = -1;
        int currentBitSetMask = 1;
        int out = 0;
        while (r != 0) {
            if ((r & 1) == 1) {
                // outの注目ビットを立てる
                out |= currentBitSetMask;

                r -= n;
            }

            r &= currentRMask;
            r >>>= 1;
            currentRMask >>>= 1;

            currentBitSetMask <<= 1;
        }

        return out;
    }

    /**
     * 符号なし long について, 2^(64) を法とした逆元を計算する.
     * 
     * <p>
     * n は奇数でなければならない.
     * </p>
     * 
     * @param n 符号なし整数
     * @return n^(-1) (mod 2^(64))
     */
    static long invModR(long n) {
        assert (n & 1L) == 1L;

        long r = 1L;
        long currentRMask = -1L;
        long currentBitSetMask = 1L;
        long out = 0;
        while (r != 0) {
            if ((r & 1L) == 1L) {
                // outの注目ビットを立てる
                out |= currentBitSetMask;

                r -= n;
            }

            r &= currentRMask;
            r >>>= 1;
            currentRMask >>>= 1;

            currentBitSetMask <<= 1;
        }

        return out;
    }
}
