/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.9
 */
package matsu.num.number.factors;

/**
 * {@code int} 型の整数が素数かどうかを判定する仕組み.
 * 
 * @author Matsuura Y.
 */
interface PrimalityInt {

    /**
     * {@code int} 型の整数 <i>n</i> が素数かどうかを判定する.
     * 
     * @param n 整数
     * @return <i>n</i> が素数なら true (<i>n</i> {@literal < 2} の場合は必ず false)
     */
    public abstract boolean isPrime(int n);
}
