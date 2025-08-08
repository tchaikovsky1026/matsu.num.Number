/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.8
 */
package matsu.num.number.modulo;

import java.util.function.LongFunction;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * {@link MontgomeryLong} のテスト.
 */
@RunWith(Enclosed.class)
final class MontgomeryLongTest {

    public static final Class<?> TEST_CLASS = MontgomeryLong.class;

    private static final LongFunction<ModulusLong> moduloGetter =
            m -> {
                if (m == 1) {
                    throw new UnsupportedOperationException();
                }
                if ((m % 2) == 0) {
                    throw new UnsupportedOperationException();
                }
                return new MontgomeryLong(m);
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
        ModulusLong getModulus(long m) {
            return moduloGetter.apply(m);
        }
    }
}
