/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.5
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
        int currentBitMask = 1;
        int out = 0;
        for (int i = 0; i < 32; i++) {
            if ((r & 1) == 1) {
                out |= currentBitMask;
                r -= n;
            }

            r >>>= 1;
            currentBitMask <<= 1;
        }

        return out;
    }

    /**
     * 符号なし long について, 2^(long) を法とした逆元を計算する.
     * 
     * <p>
     * n は奇数でなければならない.
     * </p>
     * 
     * @param n 符号なし整数
     * @return n^(-1) (mod 2^(long))
     */
    static long invModR(long n) {
        assert (n & 1L) == 1L;

        long r = 1L;
        long currentBitMask = 1L;
        long out = 0;
        for (int i = 0; i < 64; i++) {
            if ((r & 1L) == 1L) {
                out |= currentBitMask;
                r -= n;
            }

            r >>>= 1;
            currentBitMask <<= 1;
        }

        return out;
    }
}
