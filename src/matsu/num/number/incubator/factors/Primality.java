/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.10
 */
package matsu.num.number.incubator.factors;

/**
 * 整数が素数であるかを判定する機能を提供する,
 * ユーティリティクラス.
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
     * @return <i>n</i> が整数なら true (<i>n</i> {@literal < 2} の場合は必ず false)
     */
    public static boolean isPrime(int n) {
        return PRIMALITY_INT.isPrime(n);
    }

    /**
     * {@code long} 型の整数 <i>n</i> が素数かどうかを判定する.
     * 
     * @param n 整数
     * @return <i>n</i> が整数なら true (<i>n</i> {@literal < 2} の場合は必ず false)
     */
    public static boolean isPrime(long n) {
        return PRIMALITY_LONG.isPrime(n);
    }
}
