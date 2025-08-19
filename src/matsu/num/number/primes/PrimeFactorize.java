/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.15
 */
package matsu.num.number.primes;

/**
 * 整数型の素因数分解に関連する機能を扱う.
 * 
 * @author Matsuura Y.
 */
public final class PrimeFactorize {

    private static final PrimeFactorizeInt FACTORIZE_INT = new PollardBrentRhoInt();
    private static final PrimeFactorizeLong FACTORIZE_LONG = new PollardBrentRhoLong();

    private PrimeFactorize() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /**
     * {@code int} 型の 2 以上の整数 <i>n</i> を素因数分解する.
     * 
     * @param n 整数
     * @return <i>n</i> の素因数分解
     * @throws IllegalArgumentException <i>n</i> &lt; 2 の場合
     */
    public static PrimeFactorInt apply(int n) {
        return FACTORIZE_INT.apply(n);
    }

    /**
     * {@code long} 型の 2 以上の整数 <i>n</i> を素因数分解する.
     * 
     * @param n 整数
     * @return <i>n</i> の素因数分解
     * @throws IllegalArgumentException <i>n</i> &lt; 2 の場合
     */
    public static PrimeFactorLong apply(long n) {
        return FACTORIZE_LONG.apply(n);
    }
}
