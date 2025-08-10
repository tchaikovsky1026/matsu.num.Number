/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.10
 */
package matsu.num.number.factors;

import java.util.ArrayList;
import java.util.List;

/**
 * 素朴な試し割り法による {@link PrimeFactorizeInt} の実装.
 * 
 * @author Matsuura Y.
 */
final class NaiveTrialPrimeFactorizeInt implements PrimeFactorizeInt {

    /**
     * 唯一のコンストラクタ.
     */
    NaiveTrialPrimeFactorizeInt() {
        super();
    }

    @Override
    public int[] apply(int n) {
        if (n < 2) {
            throw new IllegalArgumentException("illegal: n < 2: n = " + n);
        }

        // 素数ははじく
        if (Primality.isPrime(n)) {
            return new int[] { n };
        }

        List<Integer> factor = new ArrayList<>(32);

        // 素因数2を調べる
        while ((n & 1) == 0) {
            factor.add(Integer.valueOf(2));
            n >>= 1;
        }

        // 素因数3を調べる
        while (true) {
            int q = n / 3;
            int r = n - 3 * q;
            if (r == 0) {
                factor.add(Integer.valueOf(3));
                n = q;
            } else {
                break;
            }
        }

        // 素因数5を調べる
        while (true) {
            int q = n / 5;
            int r = n - 5 * q;
            if (r == 0) {
                factor.add(Integer.valueOf(5));
                n = q;
            } else {
                break;
            }
        }

        /*
         * 試し割り法で検証すべき因数は, 6k + 1 と 6k + 5 である.
         * l = 6k とする.
         */
        for (int l = 6; l * l <= n; l += 6) {

            // 6k + 1 の検証
            int m1 = l + 1;
            while (true) {
                int q = n / m1;
                int r = n - m1 * q;
                if (r == 0) {
                    factor.add(Integer.valueOf(m1));
                    n = q;
                } else {
                    break;
                }
            }

            // 6k + 5 の検証
            int m2 = l + 5;
            while (true) {
                int q = n / m2;
                int r = n - m2 * q;
                if (r == 0) {
                    factor.add(Integer.valueOf(m2));
                    n = q;
                } else {
                    break;
                }
            }
        }

        if (n > 1) {
            factor.add(Integer.valueOf(n));
        }

        return factor.stream()
                .mapToInt(m -> m.intValue())
                .toArray();
    }
}
