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
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * {@link PrimeFactorLong} のテスト
 */
@RunWith(Enclosed.class)
final class PrimeFactorLongTest {

    public static class dividedByに関するテスト {

        private PrimeFactorLong primeFactorLong;

        @Before
        public void before_20の素因数分解の構築() {
            primeFactorLong = new PrimeFactorLong(20L, List.of(2L, 2L, 5L));
        }

        @Test
        public void test_beforeのテスト() {
            testLong(primeFactorLong);
        }

        @Test
        public void test_2を2回除くと5になる() {
            PrimeFactorLong fact5 = primeFactorLong
                    .dividedBy(2L).get()
                    .dividedBy(2L).get();

            assertThat(fact5.original(), is(5L));
            testLong(fact5);
        }

        @Test
        public void test_2を3回除くことはできない() {
            Optional<PrimeFactorLong> opFact = primeFactorLong
                    .dividedBy(2L).get()
                    .dividedBy(2L).get()
                    .dividedBy(2L);

            assertThat(opFact, is(Optional.empty()));
        }

        @Test
        public void test_5を除くと4になる() {
            PrimeFactorLong fact4 = primeFactorLong
                    .dividedBy(5L).get();

            assertThat(fact4.original(), is(4L));
            testLong(fact4);
        }

        @Test
        public void test_1を除くことはできない() {
            Optional<PrimeFactorLong> opFact = primeFactorLong
                    .dividedBy(1L);

            assertThat(opFact, is(Optional.empty()));
        }

        @Test
        public void test_2を2回と5を除くことはできない() {
            Optional<PrimeFactorLong> opFact = primeFactorLong
                    .dividedBy(2L).get()
                    .dividedBy(5L).get()
                    .dividedBy(2L);

            assertThat(opFact, is(Optional.empty()));
        }
    }

    public static class subFactorsIteratorに関するテスト {

        @Test
        public void test_元数20_イテレータの要素は10と4である() {

            PrimeFactorLong primeFactor = new PrimeFactorLong(20L, List.of(2L, 2L, 5L));
            testLong(primeFactor);

            long[] subNs = StreamSupport
                    .stream(
                            Spliterators.spliteratorUnknownSize(primeFactor.subFactorsIterator(), 0),
                            false)
                    .mapToLong(PrimeFactorLong::original)
                    .toArray();

            assertThat(subNs, is(new long[] { 10L, 4L }));
        }

        @Test
        public void test_元数10_イテレータの要素は5と2である() {

            PrimeFactorLong primeFactor = new PrimeFactorLong(10L, List.of(2L, 5L));
            testLong(primeFactor);

            long[] subNs = StreamSupport
                    .stream(
                            Spliterators.spliteratorUnknownSize(primeFactor.subFactorsIterator(), 0),
                            false)
                    .mapToLong(PrimeFactorLong::original)
                    .toArray();

            assertThat(subNs, is(new long[] { 5L, 2L }));
        }

        @Test
        public void test_元数5_イテレータの要素はなし() {

            PrimeFactorLong primeFactor = new PrimeFactorLong(5L, List.of(5L));
            testLong(primeFactor);

            long[] subNs = StreamSupport
                    .stream(
                            Spliterators.spliteratorUnknownSize(primeFactor.subFactorsIterator(), 0),
                            false)
                    .mapToLong(PrimeFactorLong::original)
                    .toArray();

            assertThat(subNs, is(new long[] {}));
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
