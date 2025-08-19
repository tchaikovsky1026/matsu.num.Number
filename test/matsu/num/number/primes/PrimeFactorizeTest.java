/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.primes;

import static matsu.num.number.primes.PrimeFactorize.*;

import java.util.function.IntFunction;
import java.util.function.LongFunction;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * {@link PrimeFactorize} のテスト.
 */
@RunWith(Enclosed.class)
final class PrimeFactorizeTest {

    public static final Class<?> TEST_CLASS = PrimeFactorize.class;

    public static class IntFactorize extends PrimeFactorizeTesting.IntFactorize {

        @Override
        IntFunction<PrimeFactorInt> getPrimeFactorize() {
            return (int n) -> apply(n);
        }

        @Override
        int max() {
            return 2_000_000_000;
        }
    }

    public static class LongFactorize extends PrimeFactorizeTesting.LongFactorize {

        @Override
        LongFunction<PrimeFactorLong> getPrimeFactorize() {
            return (long n) -> apply(n);
        }

        @Override
        long max() {
            return 3_000_000_000_000_000L;
        }
    }
}
