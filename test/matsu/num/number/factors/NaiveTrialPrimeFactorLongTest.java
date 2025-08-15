/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.11
 */
package matsu.num.number.factors;

import java.util.function.LongFunction;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * {@link NaiveTrialPrimeFactorLong} のテスト.
 */
@Deprecated
@RunWith(Enclosed.class)
final class NaiveTrialPrimeFactorLongTest {

    public static final Class<?> TEST_CLASS = NaiveTrialPrimeFactorLong.class;

    private static final PrimeFactorLong FACTORIZE_LONG = new NaiveTrialPrimeFactorLong();

    public static class LongFactorize extends PrimeFactorizeTesting.LongFactorize {

        @Override
        LongFunction<long[]> getPrimeFactorize() {
            return FACTORIZE_LONG::apply;
        }

        @Override
        long max() {
            return 3_000_000_000L;
        }
    }
}
