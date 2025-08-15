/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.factors;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * {@link Gcd} のテスト.
 */
@RunWith(Enclosed.class)
final class GcdTest {

    public static class GcdIntのテスト {

        @Test
        public void test_gcd_ランダム化() {
            int ite = 1000;

            for (int c = 0; c < ite; c++) {
                int a = ThreadLocalRandom.current().nextInt();
                int b = ThreadLocalRandom.current().nextInt();

                int result = Gcd.gcd(a, b);
                int expected = BigInteger.valueOf(a)
                        .gcd(BigInteger.valueOf(b))
                        .intValue();

                assertThat("a = " + a + ", b = " + b, result, is(expected));
            }
        }

        @Test
        public void test_gcd_0_0() {
            assertThat(Gcd.gcd(0, 0), is(0));
        }

        @Test
        public void test_gcd_0_n() {
            int ite = 10;
            for (int c = 0; c < ite; c++) {
                int n = ThreadLocalRandom.current().nextInt();
                assertThat(Gcd.gcd(0, n), is(Math.abs(n)));
            }
        }

        @Test
        public void test_gcd_MIN_VALUE() {
            assertThat(Gcd.gcd(Integer.MIN_VALUE, Integer.MIN_VALUE), is(Integer.MIN_VALUE));
        }
    }

    public static class GcdLongのテスト {

        @Test
        public void test_gcd_ランダム化() {
            int ite = 1000;

            for (int c = 0; c < ite; c++) {
                long a = ThreadLocalRandom.current().nextLong();
                long b = ThreadLocalRandom.current().nextLong();

                long result = Gcd.gcd(a, b);
                long expected = BigInteger.valueOf(a)
                        .gcd(BigInteger.valueOf(b))
                        .intValue();

                assertThat("a = " + a + ", b = " + b, result, is(expected));
            }
        }

        @Test
        public void test_gcd_0_0() {
            assertThat(Gcd.gcd(0L, 0L), is(0L));
        }

        @Test
        public void test_gcd_0_n() {
            int ite = 10;
            for (int c = 0; c < ite; c++) {
                long n = ThreadLocalRandom.current().nextInt();
                assertThat(Gcd.gcd(0, n), is(Math.abs(n)));
            }
        }

        @Test
        public void test_gcd_MIN_VALUE() {
            assertThat(Gcd.gcd(Long.MIN_VALUE, Long.MIN_VALUE), is(Long.MIN_VALUE));
        }
    }
}
