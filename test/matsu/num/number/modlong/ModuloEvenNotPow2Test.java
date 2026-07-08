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

/** {@link ModuloEvenNotPow2} クラスのテスト. */
@RunWith(Enclosed.class)
final class ModuloEvenNotPow2Test {

    public static final Class<?> TEST_CLASS = ModuloEvenNotPow2.class;

    private static final long[] DIVISORS = {
            12L, 14L, 30L, 40L, 126L, 11L * (1L << 42)
    };

    private static final LongFunction<ModuloLong> moduloGetter =
            divisor -> {
                int pow2Exponent = Long.numberOfTrailingZeros(divisor);
                long innerDivisor = divisor >> pow2Exponent;
                return new ModuloEvenNotPow2(pow2Exponent, innerDivisor);
            };

    public static class ModProd2のテスト extends ModuloTestingUtil.Prod2 {

        @DataPoints
        public static long[] divisors = DIVISORS;

        @Override
        LongFunction<ModuloLong> getModulo() {
            return moduloGetter;
        }
    }

    public static class ModProdArrayのテスト extends ModuloTestingUtil.ProdArray {

        @DataPoints
        public static long[] divisors = DIVISORS;

        @Override
        LongFunction<ModuloLong> getModulo() {
            return moduloGetter;
        }
    }

    public static class ModPowのテスト extends ModuloTestingUtil.Pow {

        @DataPoints
        public static long[] divisors = DIVISORS;

        @Override
        LongFunction<ModuloLong> getModulo() {
            return moduloGetter;
        }
    }

    public static class GcdInverseのテスト extends ModuloTestingUtil.GcdInverse {

        @DataPoints
        public static long[] divisors = DIVISORS;

        @Override
        LongFunction<ModuloLong> getModulo() {
            return moduloGetter;
        }
    }
}
