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

/**
 * {@link NaiveTrialPrimeFactorInt} のテスト.
 */
@Deprecated
@RunWith(Enclosed.class)
final class NaiveTrialPrimeFactorIntTest {

    public static final Class<?> TEST_CLASS = NaiveTrialPrimeFactorInt.class;

    private static final PrimeFactorInt FACTORIZE_INT = new NaiveTrialPrimeFactorInt();

    public static class IntFactorize extends PrimeFactorizeTesting.IntFactorize {

        @Override
        IntFunction<int[]> getPrimeFactorize() {
            return FACTORIZE_INT::apply;
        }
    }
}
