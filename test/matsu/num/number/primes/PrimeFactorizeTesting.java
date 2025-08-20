/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.11
 */
package matsu.num.number.primes;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntFunction;
import java.util.function.LongFunction;

import org.junit.Ignore;
import org.junit.Test;

/**
 * 素因数分解を実行するテスト骨格.
 * 
 * @author Matsuura Y.
 */
@Ignore
final class PrimeFactorizeTesting {

    private PrimeFactorizeTesting() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /**
     * int の素因数分解に関して検証する.
     */
    @Ignore
    static abstract class IntFactorize {

        @Test
        public final void test_素因数分解検証_ランダム化() {
            int ite = 1000;

            IntFunction<PrimeFactorInt> primeFactorize = getPrimeFactorize();

            for (int c = 0; c < ite; c++) {

                int n = 2 + ThreadLocalRandom.current().nextInt(Math.max(1, max()));

                PrimeFactorInt primeFactor = primeFactorize.apply(n);
                testInt(primeFactor);
            }
        }

        /**
         * 検証に使う素因数分解器を返す.
         * 
         * @return 素因数分解器
         */
        abstract IntFunction<PrimeFactorInt> getPrimeFactorize();

        /**
         * 検証する被除数の最大値(概算)を返す.
         * 
         * @return 被除数の最大値
         */
        int max() {
            return 1_000_000;
        }
    }

    /**
     * long の素因数分解に関して検証する.
     */
    @Ignore
    static abstract class LongFactorize {

        @Test
        public void test_素因数分解検証_ランダム化() {
            int ite = 1000;

            LongFunction<PrimeFactorLong> primeFactorize = getPrimeFactorize();

            for (int c = 0; c < ite; c++) {

                long n = 2 + ThreadLocalRandom.current().nextLong(Math.max(1L, max()));

                PrimeFactorLong primeFactor = primeFactorize.apply(n);
                testLong(primeFactor);
            }
        }

        /**
         * 検証に使う素因数分解器を返す.
         * 
         * @return 素因数分解器
         */
        abstract LongFunction<PrimeFactorLong> getPrimeFactorize();

        /**
         * 検証する被除数の最大値(概算)を返す.
         * 
         * @return 被除数の最大値
         */
        long max() {
            return 100_000_000_000L;
        }
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
