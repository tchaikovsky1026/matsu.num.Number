/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.modint;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.junit.BeforeClass;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * {@link DividendShifter} のテスト.
 */
@RunWith(Enclosed.class)
final class DividendShifterTest {

    public static final Class<?> TEST_CLASS = DividendShifter.class;

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
                    DividendShifter.computeInt(n, shift, m),
                    is(computeIntNaive(n, shift, m)));
        }

        /**
         * int型のN(0以上),m(1以上)について, {@literal (N << shift) % m} を素朴に計算する. <br>
         * ただし, シフトは適宜拡張して行われる.
         *
         * @param n N
         * @param shift shift
         * @param m m
         * @return {@literal (N << shift) % m}
         */
        private static int computeIntNaive(int n, int shift, int m) {
            assert n >= 0;
            assert m >= 1;
            assert shift >= 0;

            return BigInteger.valueOf(n)
                    .shiftLeft(shift)
                    .mod(BigInteger.valueOf(m)).intValue();
        }
    }
}
