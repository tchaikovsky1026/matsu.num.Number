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
import org.junit.runner.RunWith;

import matsu.num.number.ModuloInt;

/**
 * {@link MontgomeryBasedModuloIntFactory} クラスのテスト.
 */
@RunWith(Enclosed.class)
final class MontgomeryBasedModuloIntFactoryTest {

    public static Class<?> TEST_CLASS = MontgomeryBasedModuloIntFactory.class;
    private static final IntFunction<ModuloInt> moduloIntGetter = MontgomeryBasedModuloIntFactory::get;

    public static class ModProd2Intのテスト extends ModuloIntTesting.Prod2 {

        @Override
        ModuloInt getModulusInt(int m) {
            return moduloIntGetter.apply(m);
        }
    }

    public static class ModProdArrayIntのテスト extends ModuloIntTesting.ProdArray {

        @Override
        ModuloInt getModulusInt(int m) {
            return moduloIntGetter.apply(m);
        }
    }

    public static class ModPowIntのテスト extends ModuloIntTesting.Pow {

        @Override
        ModuloInt getModulusInt(int m) {
            return moduloIntGetter.apply(m);
        }
    }

    public static class GcdInverseIntのテスト extends ModuloIntTesting.GcdInverse {

        @Override
        ModuloInt getModulusInt(int m) {
            return moduloIntGetter.apply(m);
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
