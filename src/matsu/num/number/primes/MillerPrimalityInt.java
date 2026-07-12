/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.7.12
 */
package matsu.num.number.primes;

import matsu.num.number.ModuloInt;
import matsu.num.number.primes.Primality.PrimalityInt;

/**
 * {@code int} 型の Miller-Rabin テストによる素数判定.
 * 
 * @author Matsuura Y.
 */
final class MillerPrimalityInt implements PrimalityInt {

    private static final int[] testA = { 2, 3, 5, 7 };

    /**
     * 唯一のコンストラクタ.
     */
    MillerPrimalityInt() {
        super();
    }

    @Override
    public boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        if ((n & 1) == 0) {
            return n == 2;
        }

        // nは3以上の奇数
        // Miller-Rabin テスト
        int n_m1 = n - 1;
        int pow2Exponent = Integer.numberOfTrailingZeros(n_m1);
        int m = n_m1 >> pow2Exponent;
        ModuloInt modN = ModuloInt.get(n);

        labelA: for (int a : testA) {
            // a < n だけをテストに用いる
            if (a > n_m1) {
                // testAは昇順に並んでいるハズ
                break;
            }

            int aPow = modN.modpow(a, m);

            /*
             * 合成数の証拠である次を検証する.
             * a^m は 1 でなく, かつ,
             * a^m, a^(2m), a^(4m), a^(8m), ... , a^(2^(d-1) * m) のいずれも -1 でない.
             * 満たさない場合は早期 continue
             */
            if (aPow == 1) {
                continue;
            }
            for (int r = 0; r < pow2Exponent;
                    r++, aPow = modN.modpr(aPow, aPow)) {
                // aPow = a^(2^r m) である
                // r = 0, 1, ... , d-1 が走査される
                if (aPow == n_m1) {
                    continue labelA;
                }
            }

            // ここに到達した場合は, 合成数の証拠が得られたという意味
            return false;
        }

        return true;
    }
}
