/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.7.16
 */
package matsu.num.number.modlong;

import matsu.num.number.ModuloLong;

/**
 * {@code Modulo} の
 * {@code gcdInverse} メソッドの転送先 (委譲先) である. <br>
 * 実装を補助する役目.
 * 
 * <hr>
 * 
 * <p>
 * gcdInverse は,
 * a, m に対して
 * ar = gcd(a,m) (mod m)
 * なる r を求めることである. <br>
 * m = 1の場合は r = 0 とすればよい. <br>
 * 以下では {@literal m >= 2} とする.
 * </p>
 * 
 * @author Matsuura Y.
 */
final class GcdInverseTransfer {

    private GcdInverseTransfer() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /**
     * {@code long} 型の整数 <i>a</i> に対する,
     * <i>a</i><i>r</i> &equiv; gcd(<i>a</i>, <i>m</i>) (mod&nbsp;<i>m</i>)
     * を満たす整数 <i>r</i> のうちの1つを返す (GCD逆元). <br>
     * <i>r</i> は 0 以上 <i>m</i> 未満の値である.
     * 
     * @param a 整数 <i>a</i>
     * @param modulo mod&nbsp;<i>m</i> モジュロ
     * @return GCD逆元
     */
    static long gcdInverse(long a, ModuloLong modulo) {
        final long m = modulo.divisor();

        if (m == 1L) {
            return 0;
        }

        long v = modulo.mod(a);
        long vp = m;

        // m >= 2
        // r, rp はmod m の世界で正規化されている
        long r = 1;
        long rp = 0;
        while (vp != 0) {
            long q = v / vp;

            long vpp = v - q * vp;
            v = vp;
            vp = vpp;

            // rpp = (r - q*rp) mod m を計算する.
            long mod_q_rp = modulo.modpr(q, rp);
            long rpp = r - mod_q_rp;
            if (rpp < 0L) {
                rpp += m;
            }

            r = rp;
            rp = rpp;
        }

        return modulo.mod(r);
    }
}
