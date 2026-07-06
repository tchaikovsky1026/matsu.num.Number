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

import matsu.num.number.primes.Primality.PrimalityInt;

/**
 * 素数判定をテストするためのテスト骨格.
 * 
 * @author Matsuura Y.
 */
@Ignore
final class PrimalityIntTesting {

    private static final PrimalityInt REFERENCE = new NaivePrimalityIntForTesting();

    private PrimalityIntTesting() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /**
     * 小さい整数を網羅して検証する.
     */
    @Ignore
    static abstract class EnumerationInt {

        private static final int test_min = -5;
        private static final int test_max = 1_000_000;

        @Test
        public void test_素数であるかを検証する() {
            for (int v = test_min; v < test_max; v++) {
                boolean expected = REFERENCE.isPrime(v);
                boolean result = getPrimalityInt().isPrime(v);

                String displayString = createDisplayStringInt(v, result, expected);
                assertThat(displayString, result, is(expected));
            }
        }

        /**
         * 検証される素数判定器を返す.
         * 
         * @return 素数判定器
         */
        abstract PrimalityInt getPrimalityInt();
    }

    /**
     * ランダムな整数に対して素数判定を検証する.
     */
    @Ignore
    static abstract class RandomsInt {

        @Test
        public void test_素数検証_ランダム化() {
            int ite = 1000;

            PrimalityInt testingPrimalityInt = getPrimalityInt();

            for (int c = 0; c < ite; c++) {

                int n = ThreadLocalRandom.current().nextInt(1_000_000_000);

                boolean result = testingPrimalityInt.isPrime(n);
                boolean expected = REFERENCE.isPrime(n);

                String displayString = createDisplayStringInt(n, result, expected);
                assertThat(displayString, result, is(expected));
            }
        }

        /**
         * 検証される素数判定器を返す.
         * 
         * @return 素数判定器
         */
        abstract PrimalityInt getPrimalityInt();
    }

    private static String createDisplayStringInt(int v, boolean result, boolean expected) {
        return v + " is " + (expected ? "prime" : "not prime") +
                ", result = \"" + (result ? "prime" : "not prime") +
                "\"";
    }
}
