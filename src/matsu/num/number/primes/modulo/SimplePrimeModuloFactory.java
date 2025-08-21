/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.21
 */
package matsu.num.number.primes.modulo;

import matsu.num.number.ModuloInt;
import matsu.num.number.ModuloLong;
import matsu.num.number.primes.PrimeModuloInt;
import matsu.num.number.primes.PrimeModuloLong;

/**
 * シンプルな PrimeModulo のstaticファクトリを扱う.
 * 
 * @author Matsuura Y.
 */
public final class SimplePrimeModuloFactory {

    private SimplePrimeModuloFactory() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /**
     * {@link ModuloInt} から {@link PrimeModuloInt} を構築する.
     * 
     * @param modulo modulo
     * @return primeModuloInt
     * @throws IllegalArgumentException 法が素数でない場合
     * @throws NullPointerException 引数がnullの場合
     */
    public static PrimeModuloInt createFrom(ModuloInt modulo) {
        return new SimplePrimeModuloInt(modulo);
    }

    /**
     * {@link ModuloLong} から {@link PrimeModuloLong} を構築する.
     * 
     * @param modulo modulo
     * @return primeModuloInt
     * @throws IllegalArgumentException 法が素数でない場合
     * @throws NullPointerException 引数がnullの場合
     */
    public static PrimeModuloLong createFrom(ModuloLong modulo) {
        return new SimplePrimeModuloLong(modulo);
    }
}
