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
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.BeforeClass;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * {@link ModPositivizeInt} のテスト.
 */
@RunWith(Enclosed.class)
final class ModPositivizeIntTest {

    public static final Class<?> TEST_CLASS = ModPositivizeInt.class;

    @RunWith(Theories.class)
    public static class int型modShiftの値テスト {

        @DataPoints
        public static int[] values;

        @BeforeClass
        public static void before_1以上の整数の準備() {
            final int size = 20;

            //1以上MAX_VALUE以下の整数列
            int[] randoms = IntStream.range(0, size)
                    .map(ignore -> (ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE) + 1))
                    .toArray();
            int[] params = { 1, 4, 7, 10, 31, 32 };

            values = Stream.of(randoms, params)
                    .flatMapToInt(arr -> Arrays.stream(arr))
                    .toArray();
        }

        @Theory
        public void test_modShiftのテスト(int m, int x) {

            //負の数にして検証する
            x = -x;

            ModPositivizeInt modPositivize = new ModPositivizeInt(m);

            int xMod = modPositivize.apply(x);
            assertThat(xMod, is(greaterThanOrEqualTo(0)));

            int modDiff = BigInteger.valueOf(xMod)
                    .subtract(BigInteger.valueOf(x))
                    .mod(BigInteger.valueOf(m))
                    .intValue();

            assertThat(modDiff, is(0));
        }
    }
}
