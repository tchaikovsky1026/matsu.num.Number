/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.14
 */
package matsu.num.number.factors;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import matsu.num.number.GcdUtil;
import matsu.num.number.modulo.ModulusInt;

/**
 * {@code int} 型整数についての Pollard の &rho; 法の Brent 最適化をベースとした素因数分解.
 * 
 * @author Matsuura Y.
 */
final class PollardBrentRhoInt implements PrimeFactorizeInt {

    /**
     * rho 法に移行した場合の, 素因数の最小. <br>
     * (小さい素因数は, 試し割り法により積極的に弾きたい) <br>
     * MIN_RHO^4 > Integer.MAX_VALUE を要求する.
     */
    private static final int MIN_RHO = 500;

    /**
     * 2乗がInteger.MAX_VALUE以下である最大のint
     */
    private static final int MAX_SQRT_INT = 46340;

    /**
     * 唯一のコンストラクタ.
     */
    PollardBrentRhoInt() {
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

        // 素因数を記憶する
        List<Integer> factors = new ArrayList<>(32);

        // 素因数2, 3, 5を調べる
        while ((n & 1) == 0) {
            factors.add(Integer.valueOf(2));
            n >>= 1;
        }
        n = trial(n, 3, factors);
        n = trial(n, 5, factors);

        // 検証済みの値を表す
        int m = 5;

        /*
         * 試し割り法で検証すべき因数は, 6k + 1 と 6k + 5 である.
         * l = 6k とする.
         * 
         * MIN_RHO^4 > Integer.MAX_VALUE を満たしていることに注意.
         */
        for (int l = 6; m < MIN_RHO; l += 6) {

            // 6k + 1 の検証
            n = trial(n, l + 1, factors);

            // 6k + 5 の検証
            n = trial(n, l + 5, factors);

            m = l + 5;

            // 試し割りで完了したパターン
            if (m * m > n) {
                if (n > 1) {
                    factors.add(Integer.valueOf(n));
                }
                return toArray(factors);
            }
        }

        // 残りについて, rho 法を使う.
        while (n > 1) {
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
    private static int trial(int n, int m, List<Integer> factor) {
        while (true) {
            int r = n % m;
            if (r == 0) {
                factor.add(Integer.valueOf(m));
                n /= m;
            } else {
                break;
            }
        }
        return n;
    }

    private static int[] toArray(List<Integer> factor) {
        return factor.stream()
                .mapToInt(i -> i.intValue())
                .toArray();
    }

    private static final class RhoAlgorithm {

        private final int n;
        private final ModulusInt moduloN;

        RhoAlgorithm(int n) {
            this.n = n;
            this.moduloN = ModulusInt.get(n);
        }

        /**
         * @param n 検証する数
         * @return 残りの因数 (n が素数の場合は 1)
         */
        int factorize(List<Integer> factors) {
            if (Primality.isPrime(n)) {
                factors.add(Integer.valueOf(n));
                return 1;
            }

            /* rho法の実行 */
            final int n = this.n;

            final int m = 64; // 剰余計算のマージサイズ, 2の累乗を要求する
            final int x0 = n >> 1; // 初期値

            /*
             * 乱数生成器を,
             * y_next = (y^2 + c) mod n
             * とする (c はパラメータ).
             * オーバーフロー対策のため, c <= 0 とする.
             */
            int c = 0;
            while (true) {
                c--;
                if (c <= -n) {
                    throw new AssertionError("Bug?: Failure");
                }

                int x;
                int y = x0;
                int r = 1;
                int g = 1; // 必ず上書きされるが, ダミーを代入
                int ys = y; // 必ず上書きされるが, ダミーを代入
                CycleFinding: while (true) {
                    x = y;
                    for (int j = 0; j < r; j++) {
                        y = f(y, c);
                    }
                    int internalSize = Math.min(m, r);
                    int externalSize = r / internalSize;

                    for (int e = 0; e < externalSize; e++) {
                        ys = y;
                        int q = 1;
                        for (int i = 0; i < internalSize; i++) {
                            y = f(y, c);
                            q = moduloN.modpr(q, Math.abs(x - y));
                        }
                        g = GcdUtil.gcd(q, n);
                        if (g != 1) {
                            break CycleFinding;
                        }
                    }
                    r <<= 1;
                }

                if (g == n) {
                    for (int i = 0; i < r; i++) {
                        ys = f(ys, c);
                        g = GcdUtil.gcd(x - ys, n);
                        if (g != 1) {
                            break;
                        }
                    }
                }

                if (g < n) {
                    if (g <= MAX_SQRT_INT && g * g <= n) {
                        factors.add(Integer.valueOf(g));
                        return n / g;
                    } else {
                        factors.add(Integer.valueOf(n / g));
                        return g;
                    }
                }
            }
        }

        /**
         * 乱数を生成する
         */
        private int f(int y, int c) {
            int y2_p_c = moduloN.modpr(y, y) + c;
            if (y2_p_c < 0) {
                y2_p_c += n;
            }
            return y2_p_c;
        }
    }
}
