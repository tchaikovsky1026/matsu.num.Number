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

/** {@link ModuloMontgomery} クラスのテスト. */
@RunWith(Enclosed.class)
final class ModuloMontgomeryTest {

    public static final Class<?> TEST_CLASS = ModuloMontgomery.class;

    private static final int[] DIVISORS = {
            3, 5, 7, 9, 11, 13, 15, 17, 19,
            31, 30513, 2874127, 1000000001
    };

    public static class ModProd2のテスト extends ModuloTestingUtil.Prod2 {

        @DataPoints
        public static int[] divisors = DIVISORS;

        @Override
        IntFunction<ModuloInt> getModulo() {
            return ModuloMontgomery::new;
        }
    }

    public static class ModProdArrayのテスト extends ModuloTestingUtil.ProdArray {

        @DataPoints
        public static int[] divisors = DIVISORS;

        @Override
        IntFunction<ModuloInt> getModulo() {
            return ModuloMontgomery::new;
        }
    }

    public static class ModPowのテスト extends ModuloTestingUtil.Pow {

        @DataPoints
        public static int[] divisors = DIVISORS;

        @Override
        IntFunction<ModuloInt> getModulo() {
            return ModuloMontgomery::new;
        }
    }

    public static class GcdInverseのテスト extends ModuloTestingUtil.GcdInverse {

        @DataPoints
        public static int[] divisors = DIVISORS;

        @Override
        IntFunction<ModuloInt> getModulo() {
            return ModuloMontgomery::new;
        }
    }
}
