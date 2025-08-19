/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.15
 */
package matsu.num.number.factors;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import matsu.num.number.Gcd;
import matsu.num.number.ModuloLong;

/**
 * {@code long} 型整数についての Pollard の &rho; 法の Brent 最適化をベースとした素因数分解.
 * 
 * @author Matsuura Y.
 */
final class PollardBrentRhoLong implements PrimeFactorLong {

    /**
     * rho 法に移行した場合の, 素因数の最小. <br>
     * (小さい素因数は, 試し割り法により積極的に弾きたい)
     */
    private static final long MIN_RHO = 500L;

    /**
     * 2乗がLong.MAX_VALUE以下である最大のlong
     */
    private static final long MAX_SQRT_LONG = 3_037_000_499L;

    /**
     * 唯一のコンストラクタ.
     */
    PollardBrentRhoLong() {
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

        // 素因数を記憶する
        List<Long> factors = new ArrayList<>(64);

        // 素因数2, 3, 5を調べる
        while ((n & 1L) == 0L) {
            factors.add(Long.valueOf(2L));
            n >>= 1;
        }
        n = trial(n, 3L, factors);
        n = trial(n, 5L, factors);

        // 検証済みの値を表す
        long m = 5L;

        /*
         * 試し割り法で検証すべき因数は, 6k + 1 と 6k + 5 である.
         * l = 6k とする.
         */
        for (long l = 6L; m < MIN_RHO || (m * m) * (m * m) <= n; l += 6L) {

            // 6k + 1 の検証
            n = trial(n, l + 1L, factors);

            // 6k + 5 の検証
            n = trial(n, l + 5L, factors);

            m = l + 5L;

            // 試し割りで完了したパターン
            if (m * m > n) {
                if (n > 1L) {
                    factors.add(Long.valueOf(n));
                }
                return toArray(factors);
            }
        }

        // 残りについて, rho 法を使う.
        while (n > 1L) {
            n = new RhoAlgorithm(n).factorize(factors);
        }
        // ロー法は昇順を保証しないのでソート
        factors.sort(Comparator.naturalOrder());

        return toArray(factors);
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
            if (r == 0L) {
                factor.add(Long.valueOf(m));
                n /= m;
            } else {
                break;
            }
        }
        return n;
    }

    private static long[] toArray(List<Long> factor) {
        return factor.stream()
                .mapToLong(i -> i.longValue())
                .toArray();
    }

    private static final class RhoAlgorithm {

        private final long n;
        private final ModuloLong moduloN;

        RhoAlgorithm(long n) {
            this.n = n;
            this.moduloN = ModuloLong.get(n);
        }

        /**
         * @param n 検証する数
         * @return 残りの因数 (n が素数の場合は 1)
         */
        long factorize(List<Long> factors) {
            if (Primality.isPrime(n)) {
                factors.add(Long.valueOf(n));
                return 1L;
            }

            /* rho法の実行 */
            final long n = this.n;

            final long m = 64L; // 剰余計算のマージサイズ, 2の累乗を要求する
            final long x0 = n >> 1; // 初期値

            /*
             * 乱数生成器を,
             * y_next = (y^2 + c) mod n
             * とする (c はパラメータ).
             * オーバーフロー対策のため, c <= 0 とする.
             */
            long c = 0L;
            while (true) {
                c--;
                if (c <= -n) {
                    throw new AssertionError("Bug?: Failure");
                }

                long x;
                long y = x0;
                long r = 1L;
                long g = 1L; // 必ず上書きされるが, ダミーを代入
                long ys = y; // 必ず上書きされるが, ダミーを代入
                CycleFinding: while (true) {
                    x = y;
                    for (long j = 0L; j < r; j++) {
                        y = f(y, c);
                    }
                    long internalSize = Math.min(m, r);
                    long externalSize = r / internalSize;

                    for (long e = 0; e < externalSize; e++) {
                        ys = y;
                        long q = 1L;
                        for (long i = 0L; i < internalSize; i++) {
                            y = f(y, c);
                            q = moduloN.modpr(q, Math.abs(x - y));
                        }
                        g = Gcd.gcd(q, n);
                        if (g != 1L) {
                            break CycleFinding;
                        }
                    }
                    r <<= 1;
                }

                if (g == n) {
                    for (long i = 0; i < r; i++) {
                        ys = f(ys, c);
                        g = Gcd.gcd(x - ys, n);
                        if (g != 1) {
                            break;
                        }
                    }
                }

                if (g < n) {
                    if (g <= MAX_SQRT_LONG && g * g <= n) {
                        factors.add(Long.valueOf(g));
                        return n / g;
                    } else {
                        factors.add(Long.valueOf(n / g));
                        return g;
                    }
                }
            }
        }

        /**
         * 乱数を生成する
         * 
         * @param c 0以下
         */
        private long f(long y, long c) {
            long y2_p_c = moduloN.modpr(y, y) + c;
            if (y2_p_c < 0) {
                y2_p_c += n;
            }
            return y2_p_c;
        }
    }
}
