/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.11
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
        n = trial(n, 3L, factor);

        // 素因数5を調べる
        n = trial(n, 5L, factor);

        /*
         * 試し割り法で検証すべき因数は, 6k + 1 と 6k + 5 である.
         * l = 6k とする.
         */
        for (long l = 6L; l * l <= n; l += 6L) {

            // 6k + 1 の検証
            n = trial(n, l + 1L, factor);

            // 6k + 5 の検証
            n = trial(n, l + 5L, factor);
        }

        if (n > 1L) {
            factor.add(Long.valueOf(n));
        }

        return factor.stream()
                .mapToLong(m -> m.longValue())
                .toArray();
    }

    /**
     * 被除数 n に対して,
     * 素因数 m を調べる. <br>
     * 素因数を持つ場合, リストに追加される. <br>
     * p で割り切った商を戻り値として返す.
     * 
     * <p>
     * m が合成数の場合は, その素因数で n はすでに割り切れていることを前提とする.
     * </p>
     * 
     * @param n n
     * @param m m
     * @param factor 素因数のリスト
     * @return n を m で割り切った結果
     */
    private static long trial(long n, long m, List<Long> factor) {
        while (true) {
            long r = n % m;
            if (r == 0) {
                factor.add(Long.valueOf(m));
                n /= m;
            } else {
                break;
            }
        }
        return n;
    }
}
