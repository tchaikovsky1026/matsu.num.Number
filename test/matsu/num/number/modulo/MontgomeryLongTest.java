/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.modulo;

import java.util.function.LongFunction;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import matsu.num.number.ModuloLong;

/**
 * {@link MontgomeryLong} のテスト.
 */
@RunWith(Enclosed.class)
final class MontgomeryLongTest {

    public static final Class<?> TEST_CLASS = MontgomeryLong.class;

    private static final LongFunction<ModuloLong> moduloGetter =
            m -> {
                if (m == 1) {
                    throw new UnsupportedOperationException();
                }
                if ((m % 2) == 0) {
                    throw new UnsupportedOperationException();
                }
                return new MontgomeryLong(m);
            };

    public static class ModProd2のテスト extends ModuloLongTesting.Prod2 {

        @Override
        ModuloLong getModulusLong(long m) {
            return moduloGetter.apply(m);
        }
    }

    public static class ModProdArrayのテスト extends ModuloLongTesting.ProdArray {

        @Override
        ModuloLong getModulusLong(long m) {
            return moduloGetter.apply(m);
        }
    }

    public static class ModPowのテスト extends ModuloLongTesting.Pow {

        @Override
        ModuloLong getModulusLong(long m) {
            return moduloGetter.apply(m);
        }
    }
}
