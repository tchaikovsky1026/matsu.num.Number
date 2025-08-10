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
 * 素朴な試し割り法による {@link PrimeFactorizeLong} の実装.
 * 
 * @author Matsuura Y.
 */
final class NaiveTrialPrimeFactorizeLong implements PrimeFactorizeLong {

    /**
     * 唯一のコンストラクタ.
     */
    NaiveTrialPrimeFactorizeLong() {
        super();
    }

    @Override
    public long[] apply(long n) {
        if (n < 2L) {
            throw new IllegalArgumentException("illegal: n < 2: n = " + n);
        }

        // 素数ははじく
        if (Primality.isPrime(n)) {
            return new long[] { n };
        }

        List<Long> factor = new ArrayList<>(64);

        // 素因数2を調べる
        while ((n & 1L) == 0L) {
            factor.add(Long.valueOf(2L));
            n >>= 1;
        }

        // 素因数3を調べる
        while (true) {
            long q = n / 3L;
            long r = n - 3L * q;
            if (r == 0) {
                factor.add(Long.valueOf(3L));
                n = q;
            } else {
                break;
            }
        }

        // 素因数5を調べる
        while (true) {
            long q = n / 5L;
            long r = n - 5L * q;
            if (r == 0) {
                factor.add(Long.valueOf(5L));
                n = q;
            } else {
                break;
            }
        }

        /*
         * 試し割り法で検証すべき因数は, 6k + 1 と 6k + 5 である.
         * l = 6k とする.
         */
        for (long l = 6L; l * l <= n; l += 6L) {

            // 6k + 1 の検証
            long m1 = l + 1L;
            while (true) {
                long q = n / m1;
                long r = n - m1 * q;
                if (r == 0) {
                    factor.add(Long.valueOf(m1));
                    n = q;
                } else {
                    break;
                }
            }

            // 6k + 5 の検証
            long m2 = l + 5L;
            while (true) {
                long q = n / m2;
                long r = n - m2 * q;
                if (r == 0) {
                    factor.add(Long.valueOf(m2));
                    n = q;
                } else {
                    break;
                }
            }
        }

        if (n > 1L) {
            factor.add(Long.valueOf(n));
        }

        return factor.stream()
                .mapToLong(m -> m.longValue())
                .toArray();
    }
}
