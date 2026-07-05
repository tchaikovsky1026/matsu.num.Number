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

/** {@link ModuloEvenNotPow2} クラスのテスト. */
@RunWith(Enclosed.class)
final class ModuloEvenNotPow2Test {

    public static final Class<?> TEST_CLASS = ModuloEvenNotPow2.class;

    private static final int[] DIVISORS = {
            12, 14, 30, 40, 126, 11 * (1 << 20)
    };

    private static final IntFunction<ModuloInt> moduloGetter =
            divisor -> {
                int pow2Exponent = Integer.numberOfTrailingZeros(divisor);
                int innerDivisor = divisor >> pow2Exponent;
                return new ModuloEvenNotPow2(pow2Exponent, innerDivisor);
            };

    public static class ModProd2のテスト extends ModuloTestingUtil.Prod2 {

        @DataPoints
        public static int[] divisors = DIVISORS;

        @Override
        IntFunction<ModuloInt> getModulo() {
            return moduloGetter;
        }
    }

    public static class ModProdArrayのテスト extends ModuloTestingUtil.ProdArray {

        @DataPoints
        public static int[] divisors = DIVISORS;

        @Override
        IntFunction<ModuloInt> getModulo() {
            return moduloGetter;
        }
    }

    public static class ModPowのテスト extends ModuloTestingUtil.Pow {

        @DataPoints
        public static int[] divisors = DIVISORS;

        @Override
        IntFunction<ModuloInt> getModulo() {
            return moduloGetter;
        }
    }

    public static class GcdInverseのテスト extends ModuloTestingUtil.GcdInverse {

        @DataPoints
        public static int[] divisors = DIVISORS;

        @Override
        IntFunction<ModuloInt> getModulo() {
            return moduloGetter;
        }
    }
}
