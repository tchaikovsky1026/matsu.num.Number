/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.modint;

import java.util.function.IntFunction;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.runner.RunWith;

import matsu.num.number.ModuloInt;

/** {@link MontgomeryBasedModuloIntFactory} クラスのテスト. */
@RunWith(Enclosed.class)
final class MontgomeryBasedModuloIntFactoryTest {

    public static Class<?> TEST_CLASS = MontgomeryBasedModuloIntFactory.class;

    private static final int[] DIVISORS = {
            1, 2, 3, 4, 5, 6, 7, 8, 10, 16, 30, 31, 126, 30513, 2874127,
            1000000001, 1 << 20, 11 * (1 << 20)
    };

    private static final IntFunction<ModuloInt> moduloIntGetter = MontgomeryBasedModuloIntFactory::get;

    public static class ModProd2のテスト extends ModuloTestingUtil.Prod2 {

        @DataPoints
        public static int[] divisors = DIVISORS;

        @Override
        IntFunction<ModuloInt> getModulo() {
            return moduloIntGetter;
        }
    }

    public static class ModProdArrayのテスト extends ModuloTestingUtil.ProdArray {

        @DataPoints
        public static int[] divisors = DIVISORS;

        @Override
        IntFunction<ModuloInt> getModulo() {
            return moduloIntGetter;
        }
    }

    public static class ModPowのテスト extends ModuloTestingUtil.Pow {

        @DataPoints
        public static int[] divisors = DIVISORS;

        @Override
        IntFunction<ModuloInt> getModulo() {
            return moduloIntGetter;
        }
    }

    public static class GcdInverseのテスト extends ModuloTestingUtil.GcdInverse {

        @DataPoints
        public static int[] divisors = DIVISORS;

        @Override
        IntFunction<ModuloInt> getModulo() {
            return moduloIntGetter;
        }
    }

    public static class toString表示 {

        @Test
        public void test_toString() {
            System.out.println(TEST_CLASS.getName());
            System.out.println(moduloIntGetter.apply(1));
            System.out.println(moduloIntGetter.apply(3));
            System.out.println(moduloIntGetter.apply(4));
            System.out.println(moduloIntGetter.apply(6));
            System.out.println();
        }
    }
}
