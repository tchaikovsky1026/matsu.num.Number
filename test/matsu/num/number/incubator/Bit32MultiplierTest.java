/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

package matsu.num.number.incubator;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import matsu.num.number.speedutil.SpeedTestExecutor;

/**
 * {@link Bit32Multiplier} インターフェースとそのサブタイプのテスト.
 */
@RunWith(Enclosed.class)
final class Bit32MultiplierTest {

    public static final Class<?> TEST_CLASS = Bit32Multiplier.class;

    @RunWith(Theories.class)
    public static class Bit32MultiplierByBitOperatorの値テスト {

        private static final Bit32Multiplier BY_BIT_OPERATOR = new Bit32MultiplierByBitOperator();
        private static final Bit32Multiplier BY_LONG = new Bit32MultiplierByLong();

        /*
         * ByBitOperatorのテストにByLongを使用する.
         */

        @DataPoints
        public static int[] values;

        @BeforeClass
        public static void before_整数の準備() {
            final int size = 20;

            values = IntStream.range(0, size)
                    .map(ignore -> ThreadLocalRandom.current().nextInt())
                    .toArray();
        }

        @Theory
        public void test_符号付き積のテスト_Full(int x, int y) {
            assertThat(
                    BY_BIT_OPERATOR.multiplyFull(x, y),
                    is(BY_LONG.multiplyFull(x, y)));
        }

        @Theory
        public void test_符号付き積のテスト_High(int x, int y) {
            assertThat(
                    BY_BIT_OPERATOR.multiplyHigh(x, y),
                    is(BY_LONG.multiplyHigh(x, y)));
        }

        @Theory
        public void test_符号無し積のテスト_Full(int x, int y) {
            assertThat(
                    BY_BIT_OPERATOR.unsignedMultiplyFull(x, y),
                    is(BY_LONG.unsignedMultiplyFull(x, y)));
        }

        @Theory
        public void test_符号無し積のテスト_High(int x, int y) {
            assertThat(
                    BY_BIT_OPERATOR.unsignedMultiplyHigh(x, y),
                    is(BY_LONG.unsignedMultiplyHigh(x, y)));
        }
    }

    @Ignore
    public static class 計算時間評価 {

        private int d = 100000;

        @Test
        public void test_Bit32MultiplierByBitOperatorの実行() {
            Bit32Multiplier multiplier = new Bit32MultiplierByBitOperator();
            {
                var executor = new SpeedTestExecutor(
                        TEST_CLASS, "Bit32MultiplierByBitOperator:signed: ", 10_000_000,
                        () -> {
                            d += multiplier.multiplyHigh(d, d);
                            d += multiplier.multiplyHigh(d, d);
                            d += multiplier.multiplyHigh(d, d);
                            d += multiplier.multiplyHigh(d, d);
                            d += multiplier.multiplyHigh(d, d);
                            d += multiplier.multiplyHigh(d, d);
                            d += multiplier.multiplyHigh(d, d);
                            d += multiplier.multiplyHigh(d, d);
                            d += multiplier.multiplyHigh(d, d);
                            d += multiplier.multiplyHigh(d, d);
                        });
                executor.execute();
            }
            {
                var executor = new SpeedTestExecutor(
                        TEST_CLASS, "Bit32MultiplierByBitOperator:unsigned: ", 10_000_000,
                        () -> {
                            d += multiplier.unsignedMultiplyHigh(d, d);
                            d += multiplier.unsignedMultiplyHigh(d, d);
                            d += multiplier.unsignedMultiplyHigh(d, d);
                            d += multiplier.unsignedMultiplyHigh(d, d);
                            d += multiplier.unsignedMultiplyHigh(d, d);
                            d += multiplier.unsignedMultiplyHigh(d, d);
                            d += multiplier.unsignedMultiplyHigh(d, d);
                            d += multiplier.unsignedMultiplyHigh(d, d);
                            d += multiplier.unsignedMultiplyHigh(d, d);
                            d += multiplier.unsignedMultiplyHigh(d, d);
                        });
                executor.execute();
            }
        }

        @Test
        public void test_Bit32MultiplierByLongの実行() {
            Bit32Multiplier multiplier = new Bit32MultiplierByLong();
            {
                var executor = new SpeedTestExecutor(
                        TEST_CLASS, "Bit32MultiplierByLong:signed: ", 10_000_000,
                        () -> {
                            d += multiplier.multiplyHigh(d, d);
                            d += multiplier.multiplyHigh(d, d);
                            d += multiplier.multiplyHigh(d, d);
                            d += multiplier.multiplyHigh(d, d);
                            d += multiplier.multiplyHigh(d, d);
                            d += multiplier.multiplyHigh(d, d);
                            d += multiplier.multiplyHigh(d, d);
                            d += multiplier.multiplyHigh(d, d);
                            d += multiplier.multiplyHigh(d, d);
                            d += multiplier.multiplyHigh(d, d);
                        });
                executor.execute();
            }
            {
                var executor = new SpeedTestExecutor(
                        TEST_CLASS, "Bit32MultiplierByBitOperator:unsigned: ", 10_000_000,
                        () -> {
                            d += multiplier.unsignedMultiplyHigh(d, d);
                            d += multiplier.unsignedMultiplyHigh(d, d);
                            d += multiplier.unsignedMultiplyHigh(d, d);
                            d += multiplier.unsignedMultiplyHigh(d, d);
                            d += multiplier.unsignedMultiplyHigh(d, d);
                            d += multiplier.unsignedMultiplyHigh(d, d);
                            d += multiplier.unsignedMultiplyHigh(d, d);
                            d += multiplier.unsignedMultiplyHigh(d, d);
                            d += multiplier.unsignedMultiplyHigh(d, d);
                            d += multiplier.unsignedMultiplyHigh(d, d);
                        });
                executor.execute();
            }
        }
    }
}
