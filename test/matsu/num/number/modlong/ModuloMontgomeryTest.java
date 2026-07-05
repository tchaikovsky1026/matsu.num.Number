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

/** {@link ModuloMontgomery} クラスのテスト. */
@RunWith(Enclosed.class)
final class ModuloMontgomeryTest {

    public static final Class<?> TEST_CLASS = ModuloMontgomery.class;

    private static final long[] DIVISORS = {
            3L, 5L, 7L, 9L, 11L, 13L, 15L, 17L, 19L,
            31L, 30513L, 2874127L, 100_000_000_001L
    };

    public static class ModProd2のテスト extends ModuloTestingUtil.Prod2 {

        @DataPoints
        public static long[] divisors = DIVISORS;

        @Override
        LongFunction<ModuloLong> getModulo() {
            return ModuloMontgomery::new;
        }
    }

    public static class ModProdArrayのテスト extends ModuloTestingUtil.ProdArray {

        @DataPoints
        public static long[] divisors = DIVISORS;

        @Override
        LongFunction<ModuloLong> getModulo() {
            return ModuloMontgomery::new;
        }
    }

    public static class ModPowのテスト extends ModuloTestingUtil.Pow {

        @DataPoints
        public static long[] divisors = DIVISORS;

        @Override
        LongFunction<ModuloLong> getModulo() {
            return ModuloMontgomery::new;
        }
    }

    public static class GcdInverseのテスト extends ModuloTestingUtil.GcdInverse {

        @DataPoints
        public static long[] divisors = DIVISORS;

        @Override
        LongFunction<ModuloLong> getModulo() {
            return ModuloMontgomery::new;
        }
    }
}
