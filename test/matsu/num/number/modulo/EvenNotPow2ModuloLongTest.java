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
 * {@link EvenNotPow2ModuloLong} のテスト.
 */
@RunWith(Enclosed.class)
final class EvenNotPow2ModuloLongTest {

    public static Class<?> TEST_CLASS = EvenNotPow2ModuloLong.class;

    private static final LongFunction<ModuloLong> moduloGetter =
            divisor -> {
                int pow2Exponent = Long.numberOfTrailingZeros(divisor);
                long innerDivisor = divisor >> pow2Exponent;

                if (!(pow2Exponent >= 1 && innerDivisor != 1L)) {
                    throw new UnsupportedOperationException();
                }

                return new EvenNotPow2ModuloLong(pow2Exponent, innerDivisor);
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

    public static class GcdInverseのテスト extends ModuloLongTesting.GcdInverse {

        @Override
        ModuloLong getModulusLong(long m) {
            return moduloGetter.apply(m);
        }
    }
}
