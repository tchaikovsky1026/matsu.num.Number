/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.incubator.factors;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * {@link NaivePrimalityInt} のテスト.
 */
@RunWith(Enclosed.class)
final class NaivePrimalityIntTest {

    public static final Class<?> TEST_CLASS = NaivePrimalityInt.class;

    private static final PrimalityInt Test_Primality = new NaivePrimalityInt();

    public static class Enumeration extends PrimalityIntTesting.Enumeration {

        @Override
        PrimalityInt getPrimalityInt() {
            return Test_Primality;
        }
    }

    public static class Randoms extends PrimalityIntTesting.Randoms {

        @Override
        PrimalityInt getPrimalityInt() {
            return Test_Primality;
        }
    }
}
