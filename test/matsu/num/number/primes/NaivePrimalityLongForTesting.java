/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.7.6
 */
package matsu.num.number.primes;

import matsu.num.number.primes.Primality.PrimalityLong;

/**
 * テスト用, 素朴な試し割り法による素数判定クラス.
 * 
 * @author Matsuura Y.
 */
final class NaivePrimalityLongForTesting implements PrimalityLong {

    /**
     * 唯一のコンストラクタ.
     */
    NaivePrimalityLongForTesting() {
        super();
    }

    @Override
    public boolean isPrime(long n) {
        if (n <= 5) {
            return n == 2L || n == 3L || n == 5L;
        }

        // 2, 3, 5の倍数かどうかを検証する
        if ((n & 1L) == 0L || n % 3L == 0L || n % 5L == 0L) {
            return false;
        }

        /*
         * 試し割り法で検証すべき因数は, 6k + 1 と 6k + 5 である.
         * l = 6k とする.
         */
        for (long l = 6; l * l <= n; l += 6) {
            if (n % (l + 1L) == 0L || n % (l + 5L) == 0L) {
                return false;
            }
        }
        return true;
    }
}
