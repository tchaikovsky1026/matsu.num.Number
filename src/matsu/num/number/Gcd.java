/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.7.15
 */
package matsu.num.number;

/**
 * 整数型の最大公約数 (GCD) に関連する機能を扱う.
 * 
 * @author Matsuura Y.
 */
public final class Gcd {

    private Gcd() {
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
     * <li><i>a</i> = <i>b</i> =
     * {@link Integer#MIN_VALUE} のとき,
     * gcd(<i>a</i>, <i>b</i>) = {@link Integer#MIN_VALUE}</li>
     * </ul>
     * 
     * @param a <i>a</i>
     * @param b <i>b</i>
     * @return gcd(<i>a</i>, <i>b</i>)
     */
    public static int gcd(int a, int b) {

        /*
         * 特殊パターンを最初に処理し, a, b とも 1以上 (MAX_VALUE + 1) 以下にする.
         * (MAX_VALUE + 1 は内部的には MIN_VALUE である.)
         * a, b に含まれる素因数 2 の個数を求め, 削除し, 奇数となった a, b に対して Stein のアルゴリズムを実行.
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
        // (MAX_VALUE + 1) に対応するため, 符号なしで処理する
        a >>>= pow2TrailExponentA;
        b >>>= pow2TrailExponentB;

        // Stein のアルゴリズム
        if (a < b) {
            int t = a;
            a = b;
            b = t;
        }
        while (true) {
            // ここの時点で必ず, a >= b かつ a, bとも奇数

            while (a >= b) {
                a -= b;

                // a に含まれる素因数 2 を除去: a の末尾0を削除 (a = 0 でも正常動作)
                a >>= Integer.numberOfTrailingZeros(a);
            }

            // a >= b となるようにスワップ
            int t = a;
            a = b;
            b = t;

            // b = 0 なら gcdは a である
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
     * <li><i>a</i> = <i>b</i> =
     * {@link Long#MIN_VALUE} のとき,
     * gcd(<i>a</i>, <i>b</i>) = {@link Long#MIN_VALUE}</li>
     * </ul>
     * 
     * @param a <i>a</i>
     * @param b <i>b</i>
     * @return gcd(<i>a</i>, <i>b</i>)
     */
    public static long gcd(long a, long b) {

        /*
         * 特殊パターンを最初に処理し, a, b とも 1以上 (MAX_VALUE + 1) 以下にする.
         * (MAX_VALUE + 1 は内部的には MIN_VALUE である.)
         * a, b に含まれる素因数 2 の個数を求め, 削除し, 奇数となった a, b に対して Stein のアルゴリズムを実行.
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
        // (MAX_VALUE + 1) に対応するため, 符号なしで処理する
        a >>>= pow2TrailExponentA;
        b >>>= pow2TrailExponentB;

        // Stein のアルゴリズム
        if (a < b) {
            long t = a;
            a = b;
            b = t;
        }
        while (true) {
            // ここの時点で必ず, a >= b かつ a, bとも奇数

            while (a >= b) {
                a -= b;

                // a に含まれる素因数 2 を除去: a の末尾0を削除 (a = 0 でも正常動作)
                a >>= Long.numberOfTrailingZeros(a);
            }

            // a >= b となるようにスワップ
            long t = a;
            a = b;
            b = t;

            // b = 0 なら gcdは a である
            if (b == 0) {
                break;
            }
        }

        return a << pow2Exponent;
    }
}
