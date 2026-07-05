/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.modint;

import java.util.function.IntFunction;

import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.runner.RunWith;

import matsu.num.number.ModuloInt;

/** {@link ModuloPow2} クラスのテスト. */
@RunWith(Enclosed.class)
final class ModuloPow2Test {

    public static final Class<?> TEST_CLASS = ModuloPow2.class;

    private static final int[] DIVISORS;

    static {
        final int[] shifts = {
                1, 2, 3, 4, 6, 8, 10, 16, 25, 30
        };
        DIVISORS = new int[shifts.length];
        for (int i = 0; i < shifts.length; i++) {
            DIVISORS[i] = 1 << shifts[i];
        }
    }

    public static class ModProd2のテスト extends ModuloTestingUtil.Prod2 {

        @DataPoints
        public static int[] divisors = DIVISORS;

        @Override
        IntFunction<ModuloInt> getModulo() {
            return divisor -> new ModuloPow2(Integer.numberOfTrailingZeros(divisor));
        }
    }

    public static class ModProdArrayのテスト extends ModuloTestingUtil.ProdArray {

        @DataPoints
        public static int[] divisors = DIVISORS;

        @Override
        IntFunction<ModuloInt> getModulo() {
            return divisor -> new ModuloPow2(Integer.numberOfTrailingZeros(divisor));
        }
    }

    public static class ModPowのテスト extends ModuloTestingUtil.Pow {

        @DataPoints
        public static int[] divisors = DIVISORS;

        @Override
        IntFunction<ModuloInt> getModulo() {
            return divisor -> new ModuloPow2(Integer.numberOfTrailingZeros(divisor));
        }
    }

    public static class GcdInverseのテスト extends ModuloTestingUtil.GcdInverse {

        @DataPoints
        public static int[] divisors = DIVISORS;

        @Override
        IntFunction<ModuloInt> getModulo() {
            return divisor -> new ModuloPow2(Integer.numberOfTrailingZeros(divisor));
        }
    }
}
