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
    public static class int型の値テスト {

        /**
         * 除数. <br>
         * ホワイトボックステストとして, 境界値を強化.
         */
        @DataPoints
        public static int[] divisor = {
                3, 100,
                (1 << 30) - 1,
                (1 << 30),
                (1 << 30) + 1,
                (1 << 31) - 1
        };

        @Theory
        public void test_modShiftのテスト(int m) {
            int iteration = 1000;
            int shift = 100;

            for (int c = 0; c < iteration; c++) {
                int n = ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE) + 1;
                assertThat(
                        DividendShifterUtil.computeInt(n, shift, m),
                        is(computeIntNaive(n, shift, m)));
            }
        }

        @Theory
        public void test_modShiftのテスト_0(int m) {
            assertThat(
                    DividendShifterUtil.computeInt(0, 100, m),
                    is(computeIntNaive(0, 100, m)));
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
