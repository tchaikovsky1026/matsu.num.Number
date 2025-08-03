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
import java.util.stream.IntStream;

import org.junit.BeforeClass;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * {@link NumberUtil} クラスのテスト.
 */
@RunWith(Enclosed.class)
final class NumberUtilTest {

    @RunWith(Theories.class)
    public static class Longの積に関するビット検証 {

        @DataPoints
        public static long[] values;

        @BeforeClass
        public static void before_テスト用long値の整備() {
            int size = 100;
            values = IntStream.range(0, size)
                    .mapToLong(i -> ThreadLocalRandom.current().nextLong())
                    .toArray();

        }

        @Theory
        public void test_符号付きlong積のFullBit_配列生成(long x, long y) {
            long[] result = NumberUtil.multiplyFullLong(x, y);

            BigInteger product = BigInteger.valueOf(x).multiply(BigInteger.valueOf(y));
            long expected_high = product.shiftRight(64).longValue();
            long expected_low = product.longValue();

            assertThat("high bit", result[0], is(expected_high));
            assertThat("low bit", result[1], is(expected_low));
        }

        @Theory
        public void test_符号付きlong積のFullBit_配列に書き込み(long x, long y) {
            long[] result = new long[2];
            NumberUtil.multiplyFullLong(x, y, result);

            BigInteger product = BigInteger.valueOf(x).multiply(BigInteger.valueOf(y));
            long expected_high = product.shiftRight(64).longValue();
            long expected_low = product.longValue();

            assertThat("high bit", result[0], is(expected_high));
            assertThat("low bit", result[1], is(expected_low));
        }

        @Theory
        public void test_符号付きlong積のHighBit(long x, long y) {
            long result = NumberUtil.multiplyHighLong(x, y);

            long expected = BigInteger.valueOf(x)
                    .multiply(BigInteger.valueOf(y))
                    .shiftRight(64)
                    .longValue();

            assertThat(result, is(expected));
        }

        @Theory
        public void test_符号なしlong積のFullBit_配列生成(long x, long y) {
            long[] result = NumberUtil.unsignedMultiplyFullLong(x, y);

            BigInteger product = unsignedValueOf(x).multiply(unsignedValueOf(y));
            long expected_high = product.shiftRight(64).longValue();
            long expected_low = product.longValue();

            assertThat("high bit", result[0], is(expected_high));
            assertThat("low bit", result[1], is(expected_low));
        }

        @Theory
        public void test_符号なしlong積のFullBit_配列に書き込み(long x, long y) {
            long[] result = new long[2];
            NumberUtil.unsignedMultiplyFullLong(x, y, result);

            BigInteger product = unsignedValueOf(x).multiply(unsignedValueOf(y));
            long expected_high = product.shiftRight(64).longValue();
            long expected_low = product.longValue();

            assertThat("high bit", result[0], is(expected_high));
            assertThat("low bit", result[1], is(expected_low));
        }

        @Theory
        public void test_符号無しlong積のHighBit(long x, long y) {
            long result = NumberUtil.unsignedMultiplyHighLong(x, y);

            long expected = unsignedValueOf(x)
                    .multiply(unsignedValueOf(y))
                    .shiftRight(64)
                    .longValue();

            assertThat(result, is(expected));
        }

        /**
         * long 値を符号なし64bit整数とみなしてBigIntegerに変換する.
         * 
         * @param x x
         * @return BigInteger(unsigned x)
         */
        private static BigInteger unsignedValueOf(long x) {
            BigInteger bigX = BigInteger.valueOf(x);
            if (x < 0) {
                bigX = bigX.add(BigInteger.valueOf(1).shiftLeft(64));
            }
            return bigX;
        }
    }
}
