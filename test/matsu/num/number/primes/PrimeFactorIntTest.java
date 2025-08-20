/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

package matsu.num.number.primes;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * {@link PrimeFactorInt} のテスト
 */
@RunWith(Enclosed.class)
final class PrimeFactorIntTest {

    public static class dividedByに関するテスト {

        private PrimeFactorInt primeFactorInt;

        @Before
        public void before_20の素因数分解の構築() {
            primeFactorInt = new PrimeFactorInt(20, List.of(2, 2, 5));
        }

        @Test
        public void test_beforeのテスト() {
            testInt(primeFactorInt);
        }

        @Test
        public void test_2を2回除くと5になる() {
            PrimeFactorInt fact5 = primeFactorInt
                    .dividedBy(2).get()
                    .dividedBy(2).get();

            assertThat(fact5.original(), is(5));
            testInt(fact5);
        }

        @Test
        public void test_2を3回除くことはできない() {
            Optional<PrimeFactorInt> opFact = primeFactorInt
                    .dividedBy(2).get()
                    .dividedBy(2).get()
                    .dividedBy(2);

            assertThat(opFact, is(Optional.empty()));
        }

        @Test
        public void test_5を除くと4になる() {
            PrimeFactorInt fact4 = primeFactorInt.dividedBy(5).get();

            assertThat(fact4.original(), is(4));
            testInt(fact4);
        }

        @Test
        public void test_1を除くことはできない() {
            Optional<PrimeFactorInt> opFact = primeFactorInt
                    .dividedBy(1);

            assertThat(opFact, is(Optional.empty()));
        }

        @Test
        public void test_2を2回と5を除くことはできない() {
            Optional<PrimeFactorInt> opFact = primeFactorInt
                    .dividedBy(2).get()
                    .dividedBy(5).get()
                    .dividedBy(2);

            assertThat(opFact, is(Optional.empty()));
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
}
