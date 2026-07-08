/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.7.8
 */
package matsu.num.number.primes;

import java.util.ArrayList;
import java.util.List;

import matsu.num.number.Gcd;
import matsu.num.number.ModuloLong;
import matsu.num.number.primes.PrimeFactorize.PrimeFactorizeLong;

/**
 * {@code long} 型整数についての Pollard の &rho; 法の Brent 最適化をベースとした素因数分解.
 * 
 * @author Matsuura Y.
 */
final class PollardBrentRhoLong implements PrimeFactorizeLong {

    /*
     * Pollard-rho アルゴリズムを利用した素因数分解法:
     * 
     * (事前準備)
     * 与えられた数に対して試し割り法を実行し, 小さい素因数を弾いておく.
     * 評価する整数 n に対し, n^(1/4) 以下の素因数は含まない状態にする.
     * この時点で n は素因数を高々3個しか持たない.
     * 
     * Pollard-rho アルゴリズムは, n の約数を見つけるアルゴリズムである.
     * これを用いて, 次のように処理する.
     * 1. n が素数かを判定し, 素数なら終了.
     * 2. n が素数でない場合は約数を見つける. そして,
     * (1) 見つかった約数 g が (n^(1/2)) 以下なら, g は n の素因数である.
     * n := n/g として 1 に戻る.
     * (2) 見つかった約数 g が (n^(1/2)) より大きいなら, n/g は n の素因数である.
     * n := g として 1 に戻る.
     */

    /*
     * Pollard の rho アルゴリズム: 合成数 n に対して, 2 以上 n 未満の約数を探す.
     * 
     * 0 以上 n 未満の値を発生させる乱数 2 個の差が非自明な n の約数となることを期待する.
     * rho アルゴリズムは, x_(i+1) = f(x_i) mod n であるような写像により疑似乱数を生成し,
     * gcd(n, |x_i - x_j|) を i と j の間隔をずらしながら評価する.
     * この乱数列は, 初期部分を除いてある周期で循環する.
     * gcd(n, |x_i - x_j|) = n となった場合は |i - j| 周期で循環したことを意味する.
     * これがギリシャ文字の rho の形であるため, rho 法という.
     * 
     * そこで, i-j の値が 1 ずつ増えるように進める (i:=i+1, j:=j+2 とする) と, 間隔を増やしながら循環を検出できる.
     * これを Floyd の循環検出法という.
     * 両方を進めるのは, 初期部分を忘れるためである.
     * 
     * 乱数生成式として f(x) = x^2 + c を選ぶ.
     * 今, x_i と x_j が循環の中にあるとすると,
     * x_{i+1} - x_{j+1} = (x_i - x_j)(x_i + x_j) であるので,
     * gcd(n,|x_i - x_j|) は gcd(n,|x_{i+1} - x_{j+1}|) の約数である.
     * さらに循環するため, (循環部分の) 任意の i について, gcd(n,|x_i - x_{i + k}|) は k のみに依存する.
     * 
     * 最もシンプルな Pollard の rho アルゴリズムは,
     * i = 0, j = 1 を初期値として, i:=i+1, j:=j+2 としながら進め, gcd(n, |x_i - x_j|) を観察する.
     * gcd(n, |x_i - x_j|) >= 2 となったとき, それが n 未満ならば非自明な約数が得られたことになる.
     * n であれば, 乱数生成方法を変えて (c の値を変更して) 試す.
     * 
     * gcd(n, |x_i - x_j|) を毎回計算するのでなく,
     * gcd(n, |x_i - x_j||x_{i+1} - x_{j+2}||x_{i+2} - x_{j+4}|...) として,
     * 積について計算したとする.
     * これが 1 ならばすべて 1 である (互いに素).
     * 2 以上 n 未満なら, それは非自明な約数である.
     * n なら, そこまでの |x_i - x_j| を検証すれば, 非自明な約数または循環が検出できる.
     * これが Brent 最適化である.
     */

    /**
     * rho 法に移行した場合の, 素因数の最小. <br>
     * (小さい素因数は, 試し割り法により積極的に弾きたい)
     */
    private static final long MIN_RHO = 500L;

    /** 2乗がLong.MAX_VALUE以下である最大のlong */
    private static final long MAX_SQRT_LONG = 3_037_000_499L;

    /** 唯一のコンストラクタ. */
    PollardBrentRhoLong() {
        super();
    }

    @Override
    public PrimeFactorLong apply(long n) {
        if (n < 1L) {
            throw new IllegalArgumentException("illegal: n < 1: n = " + n);
        }

        // 1をはじく
        if (n == 1) {
            return new PrimeFactorLong(n, List.of());
        }

        // 素数ははじく
        // (本来は不要だが, 素数かどうかの評価は高速なので試す.)
        if (Primality.isPrime(n)) {
            return new PrimeFactorLong(n, List.of(Long.valueOf(n)));
        }

        final long original = n;

        // 素因数を記憶する
        List<Long> factors = new ArrayList<>(64);

        // 素因数2, 3, 5を調べる
        n = trial(n, 2L, factors);
        n = trial(n, 3L, factors);
        n = trial(n, 5L, factors);

        // 検証済みの値を表す
        long m = 5L;

        /*
         * 試し割り法で検証すべき因数は, 6k + 1 と 6k + 5 である.
         * l = 6k とする.
         */
        for (long l = 6L; m < MIN_RHO || (m * m) * (m * m) <= n; l += 6L) {

            // m^4 がオーバーフローするリスクを回避するための処置
            if (m > 1L << 16) {
                break;
            }

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
                return new PrimeFactorLong(original, factors);
            }
        }

        // 残りについて, rho 法を使う.
        while (n > 1L) {
            n = new RhoAlgorithm(n).factorize(factors);
        }

        return new PrimeFactorLong(original, factors);
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
         * 乱数を生成する. <br>
         * f = y*y + c mod n である.
         * 
         * @param c 0以下
         * @return y*y + c mod n, 0 以上 n 未満
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
