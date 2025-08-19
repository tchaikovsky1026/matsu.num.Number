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
 * {@link ModuloLongPow2} クラスのテスト.
 */
@RunWith(Enclosed.class)
final class ModuloLongPow2Test {

    public static final Class<?> TEST_CLASS = ModuloLongPow2.class;

    private static final LongFunction<ModuloLong> moduloGetter =
            m -> {
                if (!(2L <= m && m <= (1L << 62))) {
                    throw new UnsupportedOperationException();
                }
                int shift = Long.numberOfTrailingZeros(m);
                if (m - (1L << shift) != 0L) {
                    throw new UnsupportedOperationException();
                }

                return new ModuloLongPow2(shift);
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
