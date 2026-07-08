/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.modlong;

import java.util.function.LongFunction;

import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.runner.RunWith;

import matsu.num.number.ModuloLong;

/** {@link ModuloPow2} クラスのテスト. */
@RunWith(Enclosed.class)
final class ModuloPow2Test {

    public static final Class<?> TEST_CLASS = ModuloPow2.class;

    private static final long[] DIVISORS;

    static {
        final int[] shifts = {
                1, 2, 3, 4, 6, 8, 10, 16, 25, 30, 42
        };
        DIVISORS = new long[shifts.length];
        for (int i = 0; i < shifts.length; i++) {
            DIVISORS[i] = 1L << shifts[i];
        }
    }

    public static class ModProd2のテスト extends ModuloTestingUtil.Prod2 {

        @DataPoints
        public static long[] divisors = DIVISORS;

        @Override
        LongFunction<ModuloLong> getModulo() {
            return divisor -> new ModuloPow2(Long.numberOfTrailingZeros(divisor));
        }
    }

    public static class ModProdArrayのテスト extends ModuloTestingUtil.ProdArray {

        @DataPoints
        public static long[] divisors = DIVISORS;

        @Override
        LongFunction<ModuloLong> getModulo() {
            return divisor -> new ModuloPow2(Long.numberOfTrailingZeros(divisor));
        }
    }

    public static class ModPowのテスト extends ModuloTestingUtil.Pow {

        @DataPoints
        public static long[] divisors = DIVISORS;

        @Override
        LongFunction<ModuloLong> getModulo() {
            return divisor -> new ModuloPow2(Long.numberOfTrailingZeros(divisor));
        }
    }

    public static class GcdInverseのテスト extends ModuloTestingUtil.GcdInverse {

        @DataPoints
        public static long[] divisors = DIVISORS;

        @Override
        LongFunction<ModuloLong> getModulo() {
            return divisor -> new ModuloPow2(Long.numberOfTrailingZeros(divisor));
        }
    }
}
