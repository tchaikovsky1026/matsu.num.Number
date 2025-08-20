/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.primes;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import matsu.num.number.primes.Primality.PrimalityInt;

/**
 * {@link MillerPrimalityInt} のテスト.
 */
@RunWith(Enclosed.class)
final class MillerPrimalityIntTest {

    public static final Class<?> TEST_CLASS = MillerPrimalityInt.class;

    private static final PrimalityInt Test_Primality = new MillerPrimalityInt();

    public static class Enumeration extends PrimalityTesting.EnumerationInt {

        @Override
        PrimalityInt getPrimalityInt() {
            return Test_Primality;
        }
    }

    public static class Randoms extends PrimalityTesting.RandomsInt {

        @Override
        PrimalityInt getPrimalityInt() {
            return Test_Primality;
        }
    }
}
