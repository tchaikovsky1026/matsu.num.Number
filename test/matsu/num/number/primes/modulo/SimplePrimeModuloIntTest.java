/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.primes.modulo;

import java.util.function.IntFunction;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import matsu.num.number.ModuloInt;
import matsu.num.number.primes.PrimeModuloInt;

/**
 * {@link SimplePrimeModuloInt} のテスト.
 */
@RunWith(Enclosed.class)
final class SimplePrimeModuloIntTest {

    public static class PrimeModulo定型テスト extends PrimeModuloTesting.PrimeIntModulo {

        @Override
        IntFunction<PrimeModuloInt> getPrimeModuloFactory() {
            return (int p) -> new SimplePrimeModuloInt(ModuloInt.get(p));
        }
    }
}
