/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.9
 */
package matsu.num.number.primes;

import matsu.num.number.ModuloLong;

/**
 * long 型の Miller テストによる素数判定.
 * 
 * @author Matsuura Y.
 */
final class MillerPrimalityLong implements PrimalityLong {

    private static final long[] testA = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37 };

    /**
     * 唯一のコンストラクタ.
     */
    MillerPrimalityLong() {
        super();
    }

    @Override
    public boolean isPrime(long n) {
        if (n <= 1L) {
            return false;
        }
        if ((n & 1L) == 0L) {
            return n == 2L;
        }

        // nは3以上の奇数
        // Miller テスト
        long n_m1 = n - 1L;
        long pow2Exponent = Long.numberOfTrailingZeros(n_m1);
        long m = n_m1 >> pow2Exponent;
        ModuloLong modN = ModuloLong.get(n);

        labelA: for (long a : testA) {
            // a < n だけをテストに用いる
            if (a > n_m1) {
                // testAは昇順に並んでいるハズ
                break;
            }

            long aPow = modN.modpow(a, m);
            if (aPow == 1L) {
                // a^m = 1 ならば, どちらの証拠にもならない
                continue;
            }

            for (int r = 0; r < pow2Exponent;
                    r++, aPow = modN.modpr(aPow, aPow)) {
                // aPow = a^(2^r m) である
                if (aPow == n_m1) {
                    continue labelA;
                }
            }

            /*
             * ここに到達するには,
             * (n-1)/2 までに -1 が出現していない,
             * または (n-1) で 1 でないという意味である.
             */
            return false;
        }

        return true;
    }

}
