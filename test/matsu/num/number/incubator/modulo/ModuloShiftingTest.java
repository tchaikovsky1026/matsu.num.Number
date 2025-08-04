/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.incubator.modulo;

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
 * {@link ModuloShifting} のテスト.
 */
@RunWith(Enclosed.class)
final class ModuloShiftingTest {

    public static final Class<?> TEST_CLASS = ModuloShifting.class;

    @RunWith(Theories.class)
    public static class int型の値テスト {

        @DataPoints
        public static int[] values;

        @BeforeClass
        public static void before_整数の準備() {
            final int size = 20;

            values = IntStream.range(0, size)
                    .map(ignore -> (ThreadLocalRandom.current().nextInt() & 0x7FFF_FFFF))
                    .toArray();
        }

        @Theory
        public void test_modShiftのテスト(int n, int m) {
            int shift = 100;
            assertThat(
                    ModuloShifting.computeIntOptimized(n, shift, m),
                    is(ModuloShifting.computeIntNaive(n, shift, m)));
        }
    }

    @RunWith(Theories.class)
    public static class long型の値テスト {

        @DataPoints
        public static long[] values;

        @BeforeClass
        public static void before_整数の準備() {
            final int size = 20;

            values = IntStream.range(0, size)
                    .mapToLong(
                            ignore -> (ThreadLocalRandom.current().nextLong() & 0x7FFF_FFFF_FFFF_FFFFL))
                    .toArray();
        }

        @Theory
        public void test_modShiftのテスト(long n, long m) {
            int shift = 100;
            assertThat(
                    ModuloShifting.computeLongOptimized(n, shift, m),
                    is(ModuloShifting.computeLongNaive(n, shift, m)));
        }
    }
}
