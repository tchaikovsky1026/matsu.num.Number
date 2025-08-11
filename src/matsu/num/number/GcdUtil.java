/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.11
 */
package matsu.num.number;

/**
 * 整数型に関する, 最大公約数 (GCD) に関連するユーティリティ.
 * 
 * @author Matsuura Y.
 */
public final class GcdUtil {

    private GcdUtil() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /**
     * {@code int} 型整数 <i>a</i>, <i>b</i> の最大公約数
     * gcd(<i>a</i>, <i>b</i>)
     * を計算する.
     * 
     * <p>
     * 戻り値は基本的には正の数だが,
     * 例外的に, 次の値を返す.
     * </p>
     * 
     * <ul>
     * <li><i>a</i> = <i>b</i> = 0 のとき,
     * gcd(<i>a</i>, <i>b</i>) = 0</li>
     * <li><i>a</i> = <i>b</i> = -2<sup>31</sup> のとき,
     * gcd(<i>a</i>, <i>b</i>) = -2<sup>31</sup></li>
     * </ul>
     * 
     * @param a <i>a</i>
     * @param b <i>b</i>
     * @return gcd(<i>a</i>, <i>b</i>)
     */
    public static int gcd(int a, int b) {
        /*
         * Stein のアルゴリズムを改変.
         * a,bが奇数,
         * 「大きい方を |a-b|/(2^r) に置き換えていく」を繰り返す.
         * a >> b のとき, bを大きく引くようにする.
         */

        a = Math.abs(a);
        b = Math.abs(b);

        if (a == 0) {
            return b;
        }
        if (b == 0) {
            return a;
        }

        int pow2TrailExponentA = Integer.numberOfTrailingZeros(a);
        int pow2TrailExponentB = Integer.numberOfTrailingZeros(b);
        int pow2Exponent = Math.min(pow2TrailExponentA, pow2TrailExponentB);
        // Integer.MIN_VALUE に対応するため, 符号なしBitシフト
        a >>>= pow2TrailExponentA;
        b >>>= pow2TrailExponentB;

        // a >= b にする
        if (a < b) {
            int t = a;
            a = b;
            b = t;
        }

        while (true) {
            // ここの時点で, a >= b かつ a, bとも奇数である.

            //bが小さすぎる場合, aから大きく引く
            int shift = Integer.numberOfLeadingZeros(a) - Integer.numberOfLeadingZeros(b);
            if (shift >= 2) {
                a -= b << (shift - 1);
            }
            a -= b;
            a = Math.abs(a);

            //a = 0でもよい
            a >>= Integer.numberOfTrailingZeros(a);

            // a >= b にする
            if (a < b) {
                int t = a;
                a = b;
                b = t;
            }

            // b = 0なら gcdはa
            if (b == 0) {
                break;
            }
        }

        return a << pow2Exponent;
    }

    /**
     * {@code long} 型整数 <i>a</i>, <i>b</i> の最大公約数
     * gcd(<i>a</i>, <i>b</i>)
     * を計算する.
     * 
     * <p>
     * 戻り値は基本的には正の数だが,
     * 例外的に, 次の値を返す.
     * </p>
     * 
     * <ul>
     * <li><i>a</i> = <i>b</i> = 0 のとき,
     * gcd(<i>a</i>, <i>b</i>) = 0</li>
     * <li><i>a</i> = <i>b</i> = -2<sup>63</sup> のとき,
     * gcd(<i>a</i>, <i>b</i>) = -2<sup>63</sup></li>
     * </ul>
     * 
     * @param a <i>a</i>
     * @param b <i>b</i>
     * @return gcd(<i>a</i>, <i>b</i>)
     */
    public static long gcd(long a, long b) {
        /*
         * Stein のアルゴリズムを改変.
         * a,bが奇数,
         * 「大きい方を |a-b|/(2^r) に置き換えていく」を繰り返す.
         * a >> b のとき, bを大きく引くようにする.
         */

        a = Math.abs(a);
        b = Math.abs(b);

        if (a == 0) {
            return b;
        }
        if (b == 0) {
            return a;
        }

        int pow2TrailExponentA = Long.numberOfTrailingZeros(a);
        int pow2TrailExponentB = Long.numberOfTrailingZeros(b);
        int pow2Exponent = Math.min(pow2TrailExponentA, pow2TrailExponentB);
        // Integer.MIN_VALUE に対応するため, 符号なしBitシフト
        a >>>= pow2TrailExponentA;
        b >>>= pow2TrailExponentB;

        // a >= b にする
        if (a < b) {
            long t = a;
            a = b;
            b = t;
        }

        while (true) {
            // ここの時点で, a >= b かつ a, bとも奇数である.

            //bが小さすぎる場合, aから大きく引く
            long shift = Long.numberOfLeadingZeros(a) - Long.numberOfLeadingZeros(b);
            if (shift >= 2) {
                a -= b << (shift - 1);
            }
            a -= b;
            a = Math.abs(a);

            //a = 0でもよい
            a >>= Long.numberOfTrailingZeros(a);

            // a >= b にする
            if (a < b) {
                long t = a;
                a = b;
                b = t;
            }

            // b = 0なら gcdはa
            if (b == 0) {
                break;
            }
        }

        return a << pow2Exponent;
    }
}
