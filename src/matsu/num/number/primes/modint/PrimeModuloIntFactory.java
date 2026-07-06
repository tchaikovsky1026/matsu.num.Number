/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.7.6
 */
package matsu.num.number.primes.modint;

import matsu.num.number.ModuloInt;
import matsu.num.number.primes.PrimeModuloInt;

/**
 * {@link PrimeModuloInt} のパッケージ実装のstaticファクトリを扱う.
 * 
 * @author Matsuura Y.
 */
public final class PrimeModuloIntFactory {

    private PrimeModuloIntFactory() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /**
     * {@link ModuloInt} から {@link PrimeModuloInt} を構築する.
     * 
     * @param modulo modulo
     * @return primeModuloInt
     * @throws IllegalArgumentException modulo の divisor が素数でない場合
     * @throws NullPointerException 引数がnullの場合
     */
    public static PrimeModuloInt createFrom(ModuloInt modulo) {
        return new SimplePrimeModuloInt(modulo);
    }
}
