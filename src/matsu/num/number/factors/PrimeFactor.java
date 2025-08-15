/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.15
 */
package matsu.num.number.factors;

/**
 * 整数型の素因数分解に関連する機能を扱う.
 * 
 * @author Matsuura Y.
 */
public final class PrimeFactor {

    private static final PrimeFactorInt FACTORIZE_INT = new PollardBrentRhoInt();
    private static final PrimeFactorLong FACTORIZE_LONG = new PollardBrentRhoLong();

    private PrimeFactor() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /**
     * {@code int} 型の 2 以上の整数 <i>n</i> を素因数分解する.
     * 
     * <p>
     * 素因数分解の結果は, 配列として返される. <br>
     * 素数を要素とする長さ 1 以上の配列であり,
     * 昇順にソートされている. <br>
     * また, 要素の総積は <i>n</i> に一致する.
     * </p>
     * 
     * @param n 整数
     * @return <i>n</i> の素因数
     * @throws IllegalArgumentException <i>n</i> &lt; 2 の場合
     */
    public static int[] apply(int n) {
        return FACTORIZE_INT.apply(n);
    }

    /**
     * {@code long} 型の 2 以上の整数 <i>n</i> を素因数分解する.
     * 
     * <p>
     * 素因数分解の結果は, 配列として返される. <br>
     * 素数を要素とする長さ 1 以上の配列であり,
     * 昇順にソートされている. <br>
     * また, 要素の総積は <i>n</i> に一致する.
     * </p>
     * 
     * @param n 整数
     * @return <i>n</i> の素因数
     * @throws IllegalArgumentException <i>n</i> &lt; 2 の場合
     */
    public static long[] apply(long n) {
        return FACTORIZE_LONG.apply(n);
    }
}
