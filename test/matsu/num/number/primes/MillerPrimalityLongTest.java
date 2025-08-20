/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.primes;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import matsu.num.number.primes.Primality.PrimalityLong;

/**
 * {@link MillerPrimalityLong} クラスのテスト.
 */
@RunWith(Enclosed.class)
final class MillerPrimalityLongTest {

    public static final Class<?> TEST_CLASS = MillerPrimalityLong.class;

    private static final PrimalityLong Test_Primality = new MillerPrimalityLong();

    public static class Enumeration extends PrimalityTesting.EnumerationLong {

        @Override
        PrimalityLong getPrimalityLong() {
            return Test_Primality;
        }
    }

    public static class Randoms extends PrimalityTesting.RandomsLong {

        @Override
        PrimalityLong getPrimalityLong() {
            return Test_Primality;
        }
    }
}
