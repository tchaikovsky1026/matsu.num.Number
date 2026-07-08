/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.modlong;

import java.util.function.LongFunction;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.runner.RunWith;

import matsu.num.number.ModuloLong;

/** {@link MontgomeryBasedModuloLongFactory} クラスのテスト. */
@RunWith(Enclosed.class)
final class MontgomeryBasedModuloLongFactoryTest {

    public static Class<?> TEST_CLASS = MontgomeryBasedModuloLongFactory.class;

    private static final long[] DIVISORS = {
            1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 10L, 16L, 30L, 31L, 126L, 30513L, 2874127L,
            1000000001L, 1L << 20, 11L * (1L << 20),
            100_000_000_001L, 1L << 42, 11L * (1L << 42),

    };

    private static final LongFunction<ModuloLong> moduloIntGetter =
            MontgomeryBasedModuloLongFactory::get;

    public static class ModProd2のテスト extends ModuloTestingUtil.Prod2 {

        @DataPoints
        public static long[] divisors = DIVISORS;

        @Override
        LongFunction<ModuloLong> getModulo() {
            return moduloIntGetter;
        }
    }

    public static class ModProdArrayのテスト extends ModuloTestingUtil.ProdArray {

        @DataPoints
        public static long[] divisors = DIVISORS;

        @Override
        LongFunction<ModuloLong> getModulo() {
            return moduloIntGetter;
        }
    }

    public static class ModPowのテスト extends ModuloTestingUtil.Pow {

        @DataPoints
        public static long[] divisors = DIVISORS;

        @Override
        LongFunction<ModuloLong> getModulo() {
            return moduloIntGetter;
        }
    }

    public static class GcdInverseのテスト extends ModuloTestingUtil.GcdInverse {

        @DataPoints
        public static long[] divisors = DIVISORS;

        @Override
        LongFunction<ModuloLong> getModulo() {
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
