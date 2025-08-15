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

/**
 * テスト用, 素朴な試し割り法による素数判定クラス.
 * 
 * @author Matsuura Y.
 */
final class NaivePrimalityInt implements PrimalityInt {

    /**
     * 唯一のコンストラクタ.
     */
    NaivePrimalityInt() {
        super();
    }

    @Override
    public boolean isPrime(int n) {
        if (n <= 5) {
            return switch (n) {
                case 2, 3, 5:
                    yield true;
                default:
                    yield false;
            };
        }

        // 2, 3, 5の倍数かどうかを検証する
        if ((n & 1) == 0 || n % 3 == 0 || n % 5 == 0) {
            return false;
        }

        /*
         * 試し割り法で検証すべき因数は, 6k + 1 と 6k + 5 である.
         * l = 6k とする.
         */
        for (int l = 6; l * l <= n; l += 6) {
            if (n % (l + 1) == 0 || n % (l + 5) == 0) {
                return false;
            }
        }
        return true;
    }
}
