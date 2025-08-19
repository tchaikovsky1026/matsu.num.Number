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
 * {@link NaiveTrialPrimeFactorizeInt} のテスト.
 */
@Deprecated
@RunWith(Enclosed.class)
final class NaiveTrialPrimeFactorizeIntTest {

    public static final Class<?> TEST_CLASS = NaiveTrialPrimeFactorizeInt.class;

    private static final PrimeFactorizeInt FACTORIZE_INT = new NaiveTrialPrimeFactorizeInt();

    public static class IntFactorize extends PrimeFactorizeTesting.IntFactorize {

        @Override
        IntFunction<int[]> getPrimeFactorize() {
            return n -> FACTORIZE_INT.apply(n).factors();
        }
    }
}
