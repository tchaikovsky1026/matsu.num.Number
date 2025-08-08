/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

package matsu.num.number.modulo;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.junit.BeforeClass;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * {@link InverseModPow2} のテスト.
 */
@RunWith(Enclosed.class)
final class InverseModPow2Test {

    public static final Class<?> TEST_CLASS = InverseModPow2.class;

    @RunWith(Theories.class)
    public static class int型の値テスト {

        @DataPoints
        public static int[] values;

        @BeforeClass
        public static void before_整数の準備() {
            final int size = 100;

            // 奇数になるようにmodify
            values = IntStream.range(0, size)
                    .map(ignore -> (ThreadLocalRandom.current().nextInt() | 1))
                    .toArray();
        }

        @Theory
        public void test_invModRのテスト(int n) {
            assertThat(InverseModPow2.invModR(n) * n, is(1));
        }
    }

    @RunWith(Theories.class)
    public static class long型の値テスト {

        @DataPoints
        public static long[] values;

        @BeforeClass
        public static void before_整数の準備() {
            final int size = 100;

            // 奇数になるようにmodify
            values = IntStream.range(0, size)
                    .mapToLong(
                            ignore -> (ThreadLocalRandom.current().nextLong() | 1L))
                    .toArray();
        }

        @Theory
        public void test_invModRのテスト(long n) {
            assertThat(InverseModPow2.invModR(n) * n, is(1L));
        }
    }
}
