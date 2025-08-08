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

/**
 * {@link ModulusLongPow2} クラスのテスト.
 */
@RunWith(Enclosed.class)
final class ModulusLongPow2Test {

    public static final Class<?> TEST_CLASS = ModulusLongPow2.class;

    private static final LongFunction<ModulusLong> moduloGetter =
            m -> {
                if (!(2L <= m && m <= (1L << 62))) {
                    throw new UnsupportedOperationException();
                }
                int shift = Long.numberOfTrailingZeros(m);
                if (m - (1L << shift) != 0L) {
                    throw new UnsupportedOperationException();
                }

                return new ModulusLongPow2(shift);
            };

    public static class ModProd2のテスト extends ModulusLongTesting.Prod2 {

        @Override
        ModulusLong getModulusLong(long m) {
            return moduloGetter.apply(m);
        }
    }

    public static class ModProdArrayのテスト extends ModulusLongTesting.ProdArray {

        @Override
        ModulusLong getModulusLong(long m) {
            return moduloGetter.apply(m);
        }
    }

    public static class ModPowのテスト extends ModulusLongTesting.Pow {

        @Override
        ModulusLong getModulusLong(long m) {
            return moduloGetter.apply(m);
        }
    }
}
