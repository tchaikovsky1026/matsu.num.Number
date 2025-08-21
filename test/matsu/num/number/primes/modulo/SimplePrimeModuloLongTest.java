/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.primes.modulo;

import java.util.function.LongFunction;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import matsu.num.number.ModuloLong;
import matsu.num.number.primes.PrimeModuloLong;

/**
 * {@link SimplePrimeModuloInt} のテスト.
 */
@RunWith(Enclosed.class)
final class SimplePrimeModuloLongTest {

    public static class PrimeModulo定型テスト extends PrimeModuloTesting.PrimeLongModulo {

        @Override
        LongFunction<PrimeModuloLong> getPrimeModuloFactory() {
            return (long p) -> new SimplePrimeModuloLong(ModuloLong.get(p));
        }
    }
}
