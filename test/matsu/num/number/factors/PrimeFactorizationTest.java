/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.factors;

import static matsu.num.number.factors.PrimeFactorization.*;

import java.util.function.IntFunction;
import java.util.function.LongFunction;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * {@link PrimeFactorization} のテスト.
 */
@RunWith(Enclosed.class)
final class PrimeFactorizationTest {

    public static final Class<?> TEST_CLASS = PrimeFactorization.class;

    public static class IntFactorize extends PrimeFactorizeTesting.IntFactorize {

        @Override
        IntFunction<int[]> getPrimeFactorize() {
            return (int n) -> apply(n);
        }
    }

    public static class LongFactorize extends PrimeFactorizeTesting.LongFactorize {

        @Override
        LongFunction<long[]> getPrimeFactorize() {
            return (long n) -> apply(n);
        }

        @Override
        long max() {
            return 3_000_000_000L;
        }
    }
}
