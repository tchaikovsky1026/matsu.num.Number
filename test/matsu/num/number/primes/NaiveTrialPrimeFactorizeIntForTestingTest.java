/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.primes;

import java.util.function.IntFunction;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import matsu.num.number.primes.PrimeFactorize.PrimeFactorizeInt;

/**
 * {@link NaiveTrialPrimeFactorizeIntForTesting} のテスト.
 */
@RunWith(Enclosed.class)
final class NaiveTrialPrimeFactorizeIntForTestingTest {

    public static final Class<?> TEST_CLASS = NaiveTrialPrimeFactorizeIntForTesting.class;

    private static final PrimeFactorizeInt FACTORIZE_INT = new NaiveTrialPrimeFactorizeIntForTesting();

    public static class IntFactorize extends PrimeFactorizeTesting.IntFactorize {

        @Override
        IntFunction<PrimeFactorInt> getPrimeFactorize() {
            return FACTORIZE_INT::apply;
        }
    }
}
