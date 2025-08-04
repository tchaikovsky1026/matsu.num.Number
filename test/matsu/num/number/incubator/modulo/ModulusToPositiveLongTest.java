/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

package matsu.num.number.incubator.modulo;

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
 * {@link ModulusToPositiveLong} のテスト.
 */
@RunWith(Enclosed.class)
final class ModulusToPositiveLongTest {

    public static final Class<?> TEST_CLASS = ModulusToPositiveLong.class;

    @RunWith(Theories.class)
    public static class long型modShiftの値テスト {

        @DataPoints
        public static long[] values;

        @BeforeClass
        public static void before_1以上の整数の準備() {
            final int size = 20;

            //1以上MAX_VALUE以下の整数列
            values = IntStream.range(0, size)
                    .mapToLong(
                            ignore -> (ThreadLocalRandom.current().nextLong(Long.MAX_VALUE) + 1))
                    .toArray();
        }

        @Theory
        public void test_modShiftのテスト(long m, long x) {

            //負の数にして検証する
            x = -x;

            ModulusToPositiveLong modulusToPositiveLong = new ModulusToPositiveLong(m);

            long xMod = modulusToPositiveLong.toPositive(x);
            assertThat(xMod, is(greaterThanOrEqualTo(0L)));
            
            long modDiff = BigInteger.valueOf(xMod)
                    .subtract(BigInteger.valueOf(x))
                    .mod(BigInteger.valueOf(m))
                    .longValue();

            assertThat(modDiff, is(0L));
        }
    }
}
