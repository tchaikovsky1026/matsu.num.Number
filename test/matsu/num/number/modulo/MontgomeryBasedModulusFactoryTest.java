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

/**
 * {@link MontgomeryBasedModulusFactory} クラスのテスト.
 */
@RunWith(Enclosed.class)
final class MontgomeryBasedModulusFactoryTest {

    public static Class<?> TEST_CLASS = MontgomeryBasedModulusFactory.class;
    private static final IntFunction<ModulusInt> moduloIntGetter = MontgomeryBasedModulusFactory::get;
    private static final LongFunction<ModulusLong> moduloLongGetter = MontgomeryBasedModulusFactory::get;

    public static class ModProd2Intのテスト extends ModulusIntTesting.Prod2 {

        @Override
        ModulusInt getModulusInt(int m) {
            return moduloIntGetter.apply(m);
        }
    }

    public static class ModProdArrayIntのテスト extends ModulusIntTesting.ProdArray {

        @Override
        ModulusInt getModulusInt(int m) {
            return moduloIntGetter.apply(m);
        }
    }

    public static class ModPowIntのテスト extends ModulusIntTesting.Pow {

        @Override
        ModulusInt getModulusInt(int m) {
            return moduloIntGetter.apply(m);
        }
    }

    public static class ModProd2Longのテスト extends ModulusLongTesting.Prod2 {

        @Override
        ModulusLong getModulusLong(long m) {
            return moduloLongGetter.apply(m);
        }
    }

    public static class ModProdArrayLongのテスト extends ModulusLongTesting.ProdArray {

        @Override
        ModulusLong getModulusLong(long m) {
            return moduloLongGetter.apply(m);
        }
    }

    public static class ModPowLongのテスト extends ModulusLongTesting.Pow {

        @Override
        ModulusLong getModulusLong(long m) {
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
