/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.factors;

import java.util.function.IntFunction;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * {@link NaiveTrialPrimeFactorizeInt} のテスト.
 */
@RunWith(Enclosed.class)
final class NaiveTrialPrimeFactorizeIntTest {

    public static final Class<?> TEST_CLASS = NaiveTrialPrimeFactorizeInt.class;

    private static final PrimeFactorizeInt FACTORIZE_INT = new NaiveTrialPrimeFactorizeInt();

    public static class IntFactorize extends PrimeFactorizeTesting.IntFactorize {

        @Override
        IntFunction<int[]> getPrimeFactorize() {
            return FACTORIZE_INT::apply;
        }
    }
}
