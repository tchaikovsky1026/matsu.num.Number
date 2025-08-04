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
 * {@link ModulusToPositiveInt} のテスト.
 */
@RunWith(Enclosed.class)
final class ModulusToPositiveIntTest {

    public static final Class<?> TEST_CLASS = ModulusToPositiveInt.class;

    @RunWith(Theories.class)
    public static class int型modShiftの値テスト {

        @DataPoints
        public static int[] values;

        @BeforeClass
        public static void before_1以上の整数の準備() {
            final int size = 20;

            //1以上MAX_VALUE以下の整数列
            values = IntStream.range(0, size)
                    .map(ignore -> (ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE) + 1))
                    .toArray();
        }

        @Theory
        public void test_modShiftのテスト(int m, int x) {

            //負の数にして検証する
            x = -x;

            ModulusToPositiveInt modulusToPositiveInt = new ModulusToPositiveInt(m);

            int xMod = modulusToPositiveInt.toPositive(x);
            assertThat(xMod, is(greaterThanOrEqualTo(0)));

            int modDiff = BigInteger.valueOf(xMod)
                    .subtract(BigInteger.valueOf(x))
                    .mod(BigInteger.valueOf(m))
                    .intValue();

            assertThat(modDiff, is(0));
        }
    }
}
