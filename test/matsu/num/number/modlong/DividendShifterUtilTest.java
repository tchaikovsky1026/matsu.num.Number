/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.modlong;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * {@link DividendShifterUtil} のテスト.
 */
@RunWith(Enclosed.class)
final class DividendShifterUtilTest {

    public static final Class<?> TEST_CLASS = DividendShifterUtil.class;

    @RunWith(Theories.class)
    public static class long型の値テスト {

        /**
         * 除数. <br>
         * ホワイトボックステストとして, 境界値を強化.
         */
        @DataPoints
        public static long[] divisor = {
                3, 100,
                (1L << 62) - 1,
                (1L << 62),
                (1L << 62) + 1,
                (1L << 63) - 1
        };

        @Theory
        public void test_modShiftのテスト(long m) {
            int iteration = 1000;
            int shift = 100;

            for (int c = 0; c < iteration; c++) {
                long n = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE) + 1;
                assertThat(
                        DividendShifterUtil.computeLong(n, shift, m),
                        is(computeLongNaive(n, shift, m)));
            }
        }

        @Theory
        public void test_modShiftのテスト_0(long m) {
            assertThat(
                    DividendShifterUtil.computeLong(0, 100, m),
                    is(computeLongNaive(0, 100, m)));
        }

        /**
         * long型のN(0以上),m(1以上)について, {@literal (N << shift) % m} を素朴に計算する. <br>
         * ただし, シフトは適宜拡張して行われる.
         *
         * @param n N
         * @param shift shift
         * @param m m
         * @return {@literal (N << shift) % m}
         */
        private static long computeLongNaive(long n, int shift, long m) {
            assert n >= 0;
            assert m >= 1;
            assert shift >= 0;

            return BigInteger.valueOf(n)
                    .shiftLeft(shift)
                    .mod(BigInteger.valueOf(m))
                    .longValue();
        }
    }
}
