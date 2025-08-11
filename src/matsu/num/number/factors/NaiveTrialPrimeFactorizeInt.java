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
        n = trial(n, 3, factor);

        // 素因数5を調べる
        n = trial(n, 5, factor);

        /*
         * 試し割り法で検証すべき因数は, 6k + 1 と 6k + 5 である.
         * l = 6k とする.
         */
        for (int l = 6; l * l <= n; l += 6) {

            // 6k + 1 の検証
            n = trial(n, l + 1, factor);

            // 6k + 5 の検証
            n = trial(n, l + 5, factor);
        }

        if (n > 1) {
            factor.add(Integer.valueOf(n));
        }

        return factor.stream()
                .mapToInt(m -> m.intValue())
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
    private static int trial(int n, int m, List<Integer> factor) {
        while (true) {
            int q = n / m;
            int r = n - m * q;
            if (r == 0) {
                factor.add(Integer.valueOf(m));
                n = q;
            } else {
                break;
            }
        }
        return n;
    }
}
