/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.7.6
 */
package matsu.num.number.primes;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Ignore;
import org.junit.Test;

import matsu.num.number.primes.Primality.PrimalityLong;

/**
 * 素数判定をテストするためのテスト骨格.
 * 
 * @author Matsuura Y.
 */
@Ignore
final class PrimalityLongTesting {

    private static final PrimalityLong REFERENCE = new NaivePrimalityLongForTesting();

    private PrimalityLongTesting() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /**
     * 小さい整数を網羅して検証する.
     */
    @Ignore
    static abstract class EnumerationLong {

        private static final long test_min = -5L;
        private static final long test_max = 1_000_000L;

        @Test
        public void test_素数であるかを検証する() {

            for (long v = test_min; v < test_max; v++) {
                boolean expected = REFERENCE.isPrime(v);
                boolean result = getPrimalityLong().isPrime(v);

                String displayString = createDisplayStringLong(v, result, expected);
                assertThat(displayString, result, is(expected));
            }
        }

        /**
         * 検証される素数判定器を返す.
         * 
         * @return 素数判定器
         */
        abstract PrimalityLong getPrimalityLong();
    }

    /**
     * ランダムな整数に対して素数判定を検証する.
     */
    @Ignore
    static abstract class RandomsLong {

        @Test
        public void test_素数検証_ランダム化() {
            int ite = 1000;

            PrimalityLong testingPrimalityInt = getPrimalityLong();

            for (int c = 0; c < ite; c++) {

                long n = ThreadLocalRandom.current().nextLong(10_000_000_000_000L);

                boolean result = testingPrimalityInt.isPrime(n);
                boolean expected = REFERENCE.isPrime(n);

                String displayString = createDisplayStringLong(n, result, expected);
                assertThat(displayString, result, is(expected));
            }
        }

        /**
         * 検証される素数判定器を返す.
         * 
         * @return 素数判定器
         */
        abstract PrimalityLong getPrimalityLong();
    }

    private static String createDisplayStringLong(long v, boolean result, boolean expected) {
        return v + " is " + (expected ? "prime" : "not prime") +
                ", result = \"" + (result ? "prime" : "not prime") +
                "\"";
    }
}
