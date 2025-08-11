/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * {@link GcdUtil} のテスト.
 */
@RunWith(Enclosed.class)
final class GcdUtilTest {

    public static class GcdIntのテスト {

        @Test
        public void test_gcd_ランダム化() {
            int ite = 1000;

            for (int c = 0; c < ite; c++) {
                int a = ThreadLocalRandom.current().nextInt();
                int b = ThreadLocalRandom.current().nextInt();

                int result = GcdUtil.gcd(a, b);
                int expected = BigInteger.valueOf(a)
                        .gcd(BigInteger.valueOf(b))
                        .intValue();

                assertThat("a = " + a + ", b = " + b, result, is(expected));
            }
        }
    }

    public static class GcdLongのテスト {

        @Test
        public void test_gcd_ランダム化() {
            int ite = 1000;

            for (int c = 0; c < ite; c++) {
                long a = ThreadLocalRandom.current().nextLong();
                long b = ThreadLocalRandom.current().nextLong();

                long result = GcdUtil.gcd(a, b);
                long expected = BigInteger.valueOf(a)
                        .gcd(BigInteger.valueOf(b))
                        .intValue();

                assertThat("a = " + a + ", b = " + b, result, is(expected));
            }
        }
    }
}
