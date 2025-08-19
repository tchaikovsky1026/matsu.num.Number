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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;

import org.junit.Ignore;

/**
 * PrimeFactor をテストを行うためのヘルパ.
 * 
 * @author Matsuura Y.
 */
@Ignore
final class PrimeFactorTestingHelper {

    private PrimeFactorTestingHelper() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /**
     * {@link PrimeFactorInt} インスタンスが正当かどうかを検証する.
     * 
     * <p>
     * 検証される項目は次の通り.
     * </p>
     * 
     * <ul>
     * <li>素因数が素数かどうか</li>
     * <li>素因数の総積が元の数に一致するかどうか</li>
     * <li>素因数が昇順に並んでいるかどうか</li>
     * </ul>
     * 
     * @param primeFactor primeFactor
     */
    static void testInt(PrimeFactorInt primeFactor) {

        int n = primeFactor.original();
        int[] factors = primeFactor.factors();

        int prodFactors = 1;

        // 素数判定しつつ総積を計算
        for (int f : factors) {
            assertThat(
                    f + "is not prime: n = " + n + ", " + Arrays.toString(factors),
                    Primality.isPrime(f), is(true));
            prodFactors *= f;
        }

        assertThat(
                "product mismatch: n = " + n + ", " + Arrays.toString(factors),
                prodFactors, is(n));

        // 昇順の判定
        for (int i = 0; i < factors.length - 1; i++) {
            assertThat("factors is sorted?", factors[i], is(lessThanOrEqualTo(factors[i + 1])));
        }
    }

    /**
     * {@link PrimeFactorLong} インスタンスが正当かどうかを検証する.
     * 
     * <p>
     * 検証される項目は次の通り.
     * </p>
     * 
     * <ul>
     * <li>素因数が素数かどうか</li>
     * <li>素因数の総積が元の数に一致するかどうか</li>
     * <li>素因数が昇順に並んでいるかどうか</li>
     * </ul>
     * 
     * @param primeFactor primeFactor
     */
    static void testLong(PrimeFactorLong primeFactor) {

        long n = primeFactor.original();
        long[] factors = primeFactor.factors();

        long prodFactors = 1;

        // 素数判定しつつ総積を計算
        for (long f : factors) {
            assertThat(
                    f + "is not prime: n = " + n + ", " + Arrays.toString(factors),
                    Primality.isPrime(f), is(true));
            prodFactors *= f;
        }

        assertThat(
                "product mismatch: n = " + n + ", " + Arrays.toString(factors),
                prodFactors, is(n));

        // 昇順の判定
        for (int i = 0; i < factors.length - 1; i++) {
            assertThat("factors is sorted?", factors[i], is(lessThanOrEqualTo(factors[i + 1])));
        }
    }
}
