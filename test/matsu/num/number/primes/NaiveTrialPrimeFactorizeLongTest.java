/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.11
 */
package matsu.num.number.primes;

import java.util.function.LongFunction;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import matsu.num.number.primes.PrimeFactorize.PrimeFactorizeLong;

/**
 * {@link NaiveTrialPrimeFactorizeLong} のテスト.
 */
@Deprecated
@RunWith(Enclosed.class)
final class NaiveTrialPrimeFactorizeLongTest {

    public static final Class<?> TEST_CLASS = NaiveTrialPrimeFactorizeLong.class;

    private static final PrimeFactorizeLong FACTORIZE_LONG = new NaiveTrialPrimeFactorizeLong();

    public static class LongFactorize extends PrimeFactorizeTesting.LongFactorize {

        @Override
        LongFunction<PrimeFactorLong> getPrimeFactorize() {
            return FACTORIZE_LONG::apply;
        }

        @Override
        long max() {
            return 3_000_000_000L;
        }
    }
}
