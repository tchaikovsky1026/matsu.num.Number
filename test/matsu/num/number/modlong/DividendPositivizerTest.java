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
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.junit.BeforeClass;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * {@link DividendPositivizer} のテスト.
 */
@RunWith(Enclosed.class)
final class DividendPositivizerTest {

    public static final Class<?> TEST_CLASS = DividendPositivizer.class;

    @RunWith(Theories.class)
    public static class long型modShiftの値テスト {

        /**
         * x,m に用いる, 1 以上の整数の集まり.
         */
        @DataPoints
        public static long[] values;

        @BeforeClass
        public static void before_1以上の整数の準備() {
            final int size = 20;

            //1以上MAX_VALUE以下の整数列
            long[] randoms = LongStream.range(0, size)
                    .map(ignore -> (ThreadLocalRandom.current().nextLong(Long.MAX_VALUE) + 1L))
                    .toArray();
            long[] params = { 1L, 4L, 7L, 10L, 31L, 32L };

            values = Stream.of(randoms, params)
                    .flatMapToLong(arr -> Arrays.stream(arr))
                    .toArray();
        }

        @Theory
        public void test_modShiftのテスト(long m, long x) {

            //負の数にして検証する
            x = -x;

            DividendPositivizer modPositivize = new DividendPositivizer(m);

            long xMod = modPositivize.apply(x);
            assertThat(xMod, is(greaterThanOrEqualTo(0L)));

            long modDiff = BigInteger.valueOf(xMod)
                    .subtract(BigInteger.valueOf(x))
                    .mod(BigInteger.valueOf(m))
                    .longValue();

            assertThat(modDiff, is(0L));
        }
    }
}
