/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.modulo;

import java.util.function.LongFunction;

/**
 * {@link EvenNotPow2ModulusLong} のテスト.
 */
final class EvenNotPow2ModulusLongTest {

    public static Class<?> TEST_CLASS = EvenNotPow2ModulusLong.class;

    private static final LongFunction<ModulusLong> moduloGetter =
            divisor -> {
                int pow2Exponent = Long.numberOfTrailingZeros(divisor);
                long innerDivisor = divisor >> pow2Exponent;

                if (!(pow2Exponent >= 1 && innerDivisor != 1L)) {
                    throw new UnsupportedOperationException();
                }

                return new EvenNotPow2ModulusLong(pow2Exponent, innerDivisor);
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
