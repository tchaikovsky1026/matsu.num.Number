/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

package matsu.num.number.incubator.modulo;

import java.util.function.IntFunction;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * {@link MontgomeryInt} クラスのテスト.
 */
@RunWith(Enclosed.class)
final class MontgomeryIntTest {

    private static final IntFunction<ModulusInt> moduloGetter =
            m -> {
                if ((m % 2) == 0) {
                    throw new UnsupportedOperationException();
                }
                return MontgomeryInt.of(m);
            };

    public static class ModProd2のテスト extends ModulusIntTesting.Prod2 {

        @Override
        ModulusInt getModulusInt(int m) {
            return moduloGetter.apply(m);
        }
    }

    public static class ModProdArrayのテスト extends ModulusIntTesting.ProdArray {

        @Override
        ModulusInt getModulusInt(int m) {
            return moduloGetter.apply(m);
        }
    }

    public static class ModPowのテスト extends ModulusIntTesting.Pow {

        @Override
        ModulusInt getModulusInt(int m) {
            return moduloGetter.apply(m);
        }
    }
}
