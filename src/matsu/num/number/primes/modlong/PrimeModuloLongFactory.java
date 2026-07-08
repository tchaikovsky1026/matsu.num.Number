/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.7.6
 */
package matsu.num.number.primes.modlong;

import matsu.num.number.ModuloLong;
import matsu.num.number.primes.PrimeModuloLong;

/**
 * {@link PrimeModuloLong} のパッケージ実装のstaticファクトリを扱う.
 * 
 * @author Matsuura Y.
 */
public final class PrimeModuloLongFactory {

    private PrimeModuloLongFactory() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /**
     * {@link ModuloLong} から {@link PrimeModuloLong} を構築する.
     * 
     * @param modulo modulo
     * @return primeModuloLong
     * @throws IllegalArgumentException modulo の divisor が素数でない場合
     * @throws NullPointerException 引数がnullの場合
     */
    public static PrimeModuloLong createFrom(ModuloLong modulo) {
        return new SimplePrimeModuloLong(modulo);
    }
}
