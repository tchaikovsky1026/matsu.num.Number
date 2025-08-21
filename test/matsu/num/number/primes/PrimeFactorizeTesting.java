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
        public void test_素因数分解検証_1() {
            IntFunction<PrimeFactorInt> primeFactorize = getPrimeFactorize();
            int n = 1;
            PrimeFactorInt primeFactor = primeFactorize.apply(n);
            PrimeFactorIntTest.testInt(primeFactor);
        }

        @Test
        public final void test_素因数分解検証_ランダム化() {
            int ite = 1000;

            IntFunction<PrimeFactorInt> primeFactorize = getPrimeFactorize();

            for (int c = 0; c < ite; c++) {

                int n = 2 + ThreadLocalRandom.current().nextInt(Math.max(1, max()));

                PrimeFactorInt primeFactor = primeFactorize.apply(n);
                PrimeFactorIntTest.testInt(primeFactor);
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
        public void test_素因数分解検証_1() {
            LongFunction<PrimeFactorLong> primeFactorize = getPrimeFactorize();
            long n = 1L;
            PrimeFactorLong primeFactor = primeFactorize.apply(n);
            PrimeFactorLongTest.testLong(primeFactor);
        }

        @Test
        public void test_素因数分解検証_ランダム化() {
            int ite = 1000;

            LongFunction<PrimeFactorLong> primeFactorize = getPrimeFactorize();

            for (int c = 0; c < ite; c++) {

                long n = 2 + ThreadLocalRandom.current().nextLong(Math.max(1L, max()));

                PrimeFactorLong primeFactor = primeFactorize.apply(n);
                PrimeFactorLongTest.testLong(primeFactor);
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
}
