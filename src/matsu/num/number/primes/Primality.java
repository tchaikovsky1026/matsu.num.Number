/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.10
 */
package matsu.num.number.primes;

/**
 * 整数型の素数判定に関連する機能を扱う.
 * 
 * @author Matsuura Y.
 */
public final class Primality {

    private static final PrimalityInt PRIMALITY_INT = new MillerPrimalityInt();
    private static final PrimalityLong PRIMALITY_LONG = new MillerPrimalityLong();

    private Primality() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /**
     * {@code int} 型の整数 <i>n</i> が素数かどうかを判定する.
     * 
     * @param n 整数
     * @return <i>n</i> が素数なら true (<i>n</i> {@literal < 2} の場合は必ず false)
     */
    public static boolean isPrime(int n) {
        return PRIMALITY_INT.isPrime(n);
    }

    /**
     * {@code long} 型の整数 <i>n</i> が素数かどうかを判定する.
     * 
     * @param n 整数
     * @return <i>n</i> が素数なら true (<i>n</i> {@literal < 2} の場合は必ず false)
     */
    public static boolean isPrime(long n) {
        if (0 <= n && n <= Integer.MAX_VALUE) {
            return isPrime((int) n);
        }
        return PRIMALITY_LONG.isPrime(n);
    }
}
