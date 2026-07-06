/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.7.6
 */
package matsu.num.number.primes;

import matsu.num.number.ModuloInt;
import matsu.num.number.primes.Primality.PrimalityInt;

/**
 * {@code int} 型の Miller-Rabin テストによる素数判定.
 * 
 * @author Matsuura Y.
 */
final class MillerPrimalityInt implements PrimalityInt {

    /*
     * 数論的性質:
     * 
     * p を 3 以上の素数, 1 <= a < p とする.
     * また, p - 1 = 2^d * m (m は奇数, d >= 1) とする.
     * 
     * フェルマーの小定理により, a^(p-1) = a^(2^d * m) = 1 (mod p) である.
     * (以下, mod p を省略する.)
     * x^2 = 1 (mod p) であるとき, x = 1 or x = -1 である.
     * よって, 次がわかる.
     * 
     * a^(2^(d-1) * m) = 1 or -1
     * 1 のとき, a^(2^(d-2) * m) = 1 or -1
     * 1 のとき, a^(2^(d-3) * m) = 1 or -1 ...
     * 
     * すなわち, a^m からスタートし,
     * a^m, a^(2m), a^(4m), a^(8m), ... , a^(2^(d-1) * m)
     * を考えたとき, 次のどちらかが成り立つ.
     * 1. a^m = 1
     * 2. a^m, a^(2m), a^(4m), a^(8m), ... , a^(2^(d-1) * m) のいずれかは -1 である.
     */

    /*
     * Miller-Rabin 素数判定法:
     * 
     * p を 3 以上の整数とし, p が素数かどうかを判定したい.
     * p - 1 = 2^d * m (m は奇数, d >= 1) とする.
     * 次が成り立つならば, p は合成数である.
     * 
     * a^m は 1 でなく, かつ,
     * a^m, a^(2m), a^(4m), a^(8m), ... , a^(2^(d-1) * m) のいずれも -1 でない.
     * 
     * いくつかの a で実行し, 合成数でないならば, p は確率的素数である.
     * 任意の範囲で確実に素数と判定するための a は特定されないが,
     * ある範囲の p を素数と判定するために必要な a は良く調べられている.
     */

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
        // Miller-Rabin テスト
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

            /*
             * 合成数の証拠である次を検証する.
             * a^m は 1 でなく, かつ,
             * a^m, a^(2m), a^(4m), a^(8m), ... , a^(2^(d-1) * m) のいずれも -1 でない.
             * 満たさない場合は早期 continue
             */
            if (aPow == 1) {
                continue;
            }
            for (int r = 0; r < pow2Exponent;
                    r++, aPow = modN.modpr(aPow, aPow)) {
                // aPow = a^(2^r m) である
                // r = 0, 1, ... , d-1 が走査される
                if (aPow == n_m1) {
                    continue labelA;
                }
            }

            // ここに到達した場合は, 合成数の証拠が得られたという意味
            return false;
        }

        return true;
    }
}
