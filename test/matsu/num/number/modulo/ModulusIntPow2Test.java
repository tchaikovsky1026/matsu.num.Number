/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

package matsu.num.number.modulo;

import java.util.function.IntFunction;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * {@link ModulusIntPow2} クラスのテスト.
 */
@RunWith(Enclosed.class)
final class ModulusIntPow2Test {

    public static final Class<?> TEST_CLASS = ModulusIntPow2.class;

    private static final IntFunction<ModulusInt> moduloGetter =
            m -> {
                if (!(2 <= m && m <= (1 << 30))) {
                    throw new UnsupportedOperationException();
                }
                int shift = Integer.numberOfTrailingZeros(m);
                if (m - (1 << shift) != 0) {
                    throw new UnsupportedOperationException();
                }

                return new ModulusIntPow2(shift);
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
