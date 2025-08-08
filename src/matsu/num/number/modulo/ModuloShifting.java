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
 * {@literal (N << shift) % m} を計算することに関する. <br>
 * shiftによるオーバーフローを回避するように実装される.
 * 
 * @author Matsuura Y.
 */
final class ModuloShifting {
    private ModuloShifting() {
        //インスタンス化不可
        throw new AssertionError();
    }

    /**
     * int型のN(0以上),m(1以上)について, {@literal (N << shift) % m}
     * を計算する.
     *
     * @param n N
     * @param shift shift
     * @param m m
     * @return {@literal (N << shift) % m}
     */
    static int computeInt(int n, int shift, int m) {
        assert n >= 0;
        assert m >= 1;
        assert shift >= 0;

        if (shift == 0) {
            return n % m;
        }

        /*
         * 除数が2^30 未満の場合,
         * "シフトにより31bitまでシフトして剰余をとる" を繰り返す.
         * 除数が2^30 以上の場合, これは31bitなので,
         * "シフトにより32bitまでシフトし, 除数を引く"を繰り返す.
         */
        if (m < (1 << 30)) {
            // m < 2^30
            while (shift > 0) {
                int currentShift = Math.min(shift, Integer.numberOfLeadingZeros(n) - 1);

                n <<= currentShift;
                n %= m;
                shift -= currentShift;
            }
            return n;
        } else {
            // m >= 2^30

            //最初にnを正規化しておく
            while (n < 0 || n >= m) {
                n -= m;
            }

            for (; shift > 0; shift--) {
                n <<= 1;
                //左シフトは2倍なので, 1回の判定で必ず正規化される.
                if (n < 0 || n >= m) {
                    n -= m;
                }
            }
            return n;
        }
    }

    /**
     * long型のN(0以上),m(1以上)について, {@literal (N << shift) % m}
     * を計算する.
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

        if (shift == 0) {
            return n % m;
        }

        /*
         * 除数が2^62 未満の場合,
         * "シフトにより63bitまでシフトして剰余をとる" を繰り返す.
         * 除数が2^62 以上の場合, これは63bitなので,
         * "シフトにより64bitまでシフトし, 除数を引く"を繰り返す.
         */
        if (m < (1L << 62)) {
            // m < 2^62
            while (shift > 0) {
                int currentShift = Math.min(shift, Long.numberOfLeadingZeros(n) - 1);

                n <<= currentShift;
                n %= m;
                shift -= currentShift;
            }
            return n;
        } else {
            // m >= 2^62

            //最初にnを正規化しておく
            while (n < 0 || n >= m) {
                n -= m;
            }

            for (; shift > 0; shift--) {
                n <<= 1;
                //左シフトは2倍なので, 1回の判定で必ず正規化される.
                if (n < 0 || n >= m) {
                    n -= m;
                }
            }
            return n;
        }
    }
}
