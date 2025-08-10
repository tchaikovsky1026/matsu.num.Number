/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.factors;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * {@link NaivePrimalityLong} のテスト.
 */
@RunWith(Enclosed.class)
final class NaivePrimalityLongTest {

    public static final Class<?> TEST_CLASS = NaivePrimalityLong.class;

    private static final PrimalityLong Test_Primality = new NaivePrimalityLong();

    public static class Enumeration extends PrimalityLongTesting.Enumeration {

        @Override
        PrimalityLong getPrimalityLong() {
            return Test_Primality;
        }
    }
}
