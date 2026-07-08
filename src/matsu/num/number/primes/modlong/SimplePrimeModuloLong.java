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
import matsu.num.number.primes.PrimeFactorLong;
import matsu.num.number.primes.PrimeFactorize;
import matsu.num.number.primes.PrimeModuloLong;

/**
 * {@link PrimeModuloLong} の実装.
 * 
 * @author Matsuura Y.
 */
final class SimplePrimeModuloLong extends SkeletalPrimeModuloLong {

    /*
     * 素数を法とするモジュロ演算における, 位数と原始根に関する.
     * 除数を p (素数) とする.
     * 
     * 1 <= a < p である a について,
     * a の位数とは, a^m = 1 (mod p) であるような最小の正整数 m である.
     * 位数の性質として次が成り立つ.
     * 
     * m は (p-1) の約数である.
     * a^q = 1 (mod p) を満たすすべての q は m の倍数である.
     * 
     * 位数計算アルゴリズム:
     * 1. a^q = 1 (mod p) なる q がわかっているとする.
     * 2. q の素因数の集合 {p_1, p_2, ...} を考え, q_k = q/p_k とし, a^(q_k) mod p を計算する.
     * 3. a^(q_k) = 1 (mod p) なる q_k があった場合, q := q_k として 2 にもどる.
     * (一度見つかればそれ以降の k を検証する必要はない).
     * ない場合は m = q である.
     * 
     * 初期値は q = p-1 とする.
     * 
     * 
     * 1 <= a < p である a について,
     * a が p を法としての原始根であるとは, a の位数が p-1 であることである.
     * a が原始根であるかを判定するには,
     * q の素因数の集合 {p_1, p_2, ...} を考え, q_k = q/p_k とし,
     * すべての k について a^(q_k) != 1 (mod p) を確かめればよい.
     */

    /**
     * p-1の素因数分解を扱う.
     */
    private final PrimeFactorLong factorOfPm1;

    /**
     * 唯一のコンストラクタ. <br>
     * 素数を法とする {@link ModuloLong} を渡して PrimeModuloLong を構築する.
     * 
     * @param modulo
     * @throws IllegalArgumentException modulo の divisor が素数でない場合
     * @throws NullPointerException 引数がnullの場合
     */
    SimplePrimeModuloLong(ModuloLong modulo) {
        // 素数でない場合はスーパークラスのコンストラクタで例外スロー
        super(modulo);

        this.factorOfPm1 = PrimeFactorize.apply(modulo.divisor() - 1L);
    }

    @Override
    public long primitiveRoot() {
        long p = this.divisor();
        for (long a = 1L; a < p; a++) {
            if (this.isPrimitiveRoot(a)) {
                return a;
            }
        }

        throw new AssertionError("Bug: primitive root is not found.");
    }

    @Override
    long orderConcrete(long a) {
        PrimeFactorLong orderCandidate = factorOfPm1;

        /*
         * 位数は a^q = 1 (mod p) なる q の約数であることを使う.
         * 
         * q の素因数の集合 {p_1, p_2, ...} を考え, q_k = q/p_k とし,
         * a^(q_k) mod p を計算する.
         * 
         * それらが 1 に一致しなければ q は位数であり, 一致すれば q := q_k としてさらに約数を考える.
         */
        candidateValidation: while (true) {
            for (PrimeFactorLong sub : orderCandidate.subFactorsCollection()) {
                if (this.modpow(a, sub.original()) == 1) {
                    orderCandidate = sub;
                    continue candidateValidation;
                }
            }

            // sub.original の mod p がどれも1でないなら, それは位数である
            break;
        }

        return orderCandidate.original();
    }

    @Override
    boolean isPrimitiveRootConcrete(long a) {

        /*
         * q=p-1 の素因数の集合 {p_1, p_2, ...} を考え, q_k = q/p_k とし,
         * すべての k について a^(q_k) != 1 mod p かどうかを確かめる.
         */
        for (PrimeFactorLong sub : factorOfPm1.subFactorsCollection()) {
            if (this.modpow(a, sub.original()) == 1) {
                return false;
            }
        }

        return true;
    }
}
