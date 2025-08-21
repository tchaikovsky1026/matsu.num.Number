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
import matsu.num.number.primes.PrimeFactorInt;
import matsu.num.number.primes.PrimeFactorize;

/**
 * {@code int} 型の素数を法とするモジュロ演算の実装.
 * 
 * @author Matsuura Y.
 */
final class SimplePrimeModuloInt extends SkeletalPrimeModuloInt {

    /**
     * p-1の素因数分解を扱う.
     */
    private final PrimeFactorInt factorOfPm1;

    /**
     * 唯一のコンストラクタ. <br>
     * 素数を法とする {@link ModuloInt} を渡して PrimeModuloInt を構築する.
     * 
     * @param modulo
     * @throws IllegalArgumentException 法が素数でない場合
     * @throws NullPointerException 引数がnullの場合
     */
    SimplePrimeModuloInt(ModuloInt modulo) {
        // 素数でない場合はスーパークラスのコンストラクタで例外スロー
        super(modulo);

        this.factorOfPm1 = PrimeFactorize.apply(modulo.divisor() - 1);
    }

    @Override
    public int primitiveRoot() {
        int p = this.divisor();
        for (int a = 1; a < p; a++) {
            if (this.isPrimitiveRoot(a)) {
                return a;
            }
        }

        throw new AssertionError("Bug: primitive root is not found.");
    }

    @Override
    int orderConcrete(int a) {
        PrimeFactorInt orderCandidate = factorOfPm1;

        candidateValidation: while (true) {
            for (PrimeFactorInt sub : orderCandidate.subFactorsCollection()) {
                if (this.modpow(a, sub.original()) == 1) {
                    orderCandidate = sub;
                    continue candidateValidation;
                }
            }

            // sub.ogininal の mod p がどれも1でないなら, それは位数である
            break;
        }

        return orderCandidate.original();
    }

    @Override
    boolean isPrimitiveRootConcrete(int a) {

        for (PrimeFactorInt sub : factorOfPm1.subFactorsCollection()) {
            if (this.modpow(a, sub.original()) == 1) {
                return false;
            }
        }

        return true;
    }
}
