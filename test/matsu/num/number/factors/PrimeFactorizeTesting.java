/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.11
 */
package matsu.num.number.factors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

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

            IntFunction<int[]> primeFactorize = getPrimeFactorize();

            for (int c = 0; c < ite; c++) {

                int n = 2 + ThreadLocalRandom.current().nextInt(Math.max(1, max()));
                int[] factors = primeFactorize.apply(n);
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
            }
        }

        /**
         * 検証に使う素因数分解器を返す.
         * 
         * @return 素因数分解器
         */
        abstract IntFunction<int[]> getPrimeFactorize();

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

            LongFunction<long[]> primeFactorize = getPrimeFactorize();

            for (int c = 0; c < ite; c++) {

                long n = 2 + ThreadLocalRandom.current().nextLong(Math.max(1L, max()));
                long[] factors = primeFactorize.apply(n);
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
            }
        }

        /**
         * 検証に使う素因数分解器を返す.
         * 
         * @return 素因数分解器
         */
        abstract LongFunction<long[]> getPrimeFactorize();

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
