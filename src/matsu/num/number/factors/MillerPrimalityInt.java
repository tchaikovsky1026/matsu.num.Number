/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.9
 */
package matsu.num.number.factors;

import matsu.num.number.modulo.ModuloInt;

/**
 * int 型の Miller テストによる素数判定.
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
        // Miller テスト
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
            if (aPow == 1) {
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
