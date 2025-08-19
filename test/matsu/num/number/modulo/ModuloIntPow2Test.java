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

import matsu.num.number.ModuloInt;

/**
 * {@link ModuloIntPow2} クラスのテスト.
 */
@RunWith(Enclosed.class)
final class ModuloIntPow2Test {

    public static final Class<?> TEST_CLASS = ModuloIntPow2.class;

    private static final IntFunction<ModuloInt> moduloGetter =
            m -> {
                if (!(2 <= m && m <= (1 << 30))) {
                    throw new UnsupportedOperationException();
                }
                int shift = Integer.numberOfTrailingZeros(m);
                if (m - (1 << shift) != 0) {
                    throw new UnsupportedOperationException();
                }

                return new ModuloIntPow2(shift);
            };

    public static class ModProd2のテスト extends ModuloIntTesting.Prod2 {

        @Override
        ModuloInt getModulusInt(int m) {
            return moduloGetter.apply(m);
        }
    }

    public static class ModProdArrayのテスト extends ModuloIntTesting.ProdArray {

        @Override
        ModuloInt getModulusInt(int m) {
            return moduloGetter.apply(m);
        }
    }

    public static class ModPowのテスト extends ModuloIntTesting.Pow {

        @Override
        ModuloInt getModulusInt(int m) {
            return moduloGetter.apply(m);
        }
    }
}
