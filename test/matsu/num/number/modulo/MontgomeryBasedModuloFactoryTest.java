/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.modulo;

import java.util.function.IntFunction;
import java.util.function.LongFunction;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import matsu.num.number.ModuloInt;
import matsu.num.number.ModuloLong;

/**
 * {@link MontgomeryBasedModuloFactory} クラスのテスト.
 */
@RunWith(Enclosed.class)
final class MontgomeryBasedModuloFactoryTest {

    public static Class<?> TEST_CLASS = MontgomeryBasedModuloFactory.class;
    private static final IntFunction<ModuloInt> moduloIntGetter = MontgomeryBasedModuloFactory::get;
    private static final LongFunction<ModuloLong> moduloLongGetter = MontgomeryBasedModuloFactory::get;

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

    public static class ModProd2Longのテスト extends ModuloLongTesting.Prod2 {

        @Override
        ModuloLong getModulusLong(long m) {
            return moduloLongGetter.apply(m);
        }
    }

    public static class ModProdArrayLongのテスト extends ModuloLongTesting.ProdArray {

        @Override
        ModuloLong getModulusLong(long m) {
            return moduloLongGetter.apply(m);
        }
    }

    public static class ModPowLongのテスト extends ModuloLongTesting.Pow {

        @Override
        ModuloLong getModulusLong(long m) {
            return moduloLongGetter.apply(m);
        }
    }

    public static class GcdInverseLongのテスト extends ModuloLongTesting.GcdInverse {

        @Override
        ModuloLong getModulusLong(long m) {
            return moduloLongGetter.apply(m);
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
            System.out.println(moduloLongGetter.apply(1));
            System.out.println(moduloLongGetter.apply(3));
            System.out.println(moduloLongGetter.apply(4));
            System.out.println(moduloLongGetter.apply(6));
            System.out.println();
        }
    }
}
