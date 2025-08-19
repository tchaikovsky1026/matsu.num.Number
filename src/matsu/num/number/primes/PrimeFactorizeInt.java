/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.19
 */
package matsu.num.number.primes;

/**
 * {@code int} 型の整数を素因数分解する仕組み.
 * 
 * @author Matsuura Y.
 */
interface PrimeFactorizeInt {

    /**
     * {@code int} 型の 2 以上の整数 <i>n</i> を素因数分解する.
     * 
     * @param n 整数
     * @return <i>n</i> の素因数分解
     * @throws IllegalArgumentException <i>n</i> &lt; 2 の場合
     */
    public abstract PrimeFactorInt apply(int n);
}
