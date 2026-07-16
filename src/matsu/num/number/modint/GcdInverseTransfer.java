/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.7.16
 */
package matsu.num.number.modint;

import matsu.num.number.ModuloInt;

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
     * {@code int} 型の整数 <i>a</i> に対する,
     * <i>a</i><i>r</i> &equiv; gcd(<i>a</i>, <i>m</i>) (mod&nbsp;<i>m</i>)
     * を満たす整数 <i>r</i> のうちの1つを返す (GCD逆元). <br>
     * <i>r</i> は 0 以上 <i>m</i> 未満の値である.
     * 
     * @param a 整数 <i>a</i>
     * @param modulo mod&nbsp;<i>m</i> モジュロ
     * @return GCD逆元
     */
    static int gcdInverse(int a, ModuloInt modulo) {
        final int m = modulo.divisor();

        if (m == 1) {
            return 0;
        }

        int v = modulo.mod(a);
        int vp = m;

        // m >= 2
        // r, rp はmod m の世界で正規化されている
        int r = 1;
        int rp = 0;
        while (vp != 0) {
            int q = v / vp;

            int vpp = v - q * vp;
            v = vp;
            vp = vpp;

            // rpp = (r - q*rp) mod m を計算する.
            int mod_q_rp = modulo.modpr(q, rp);
            int rpp = r - mod_q_rp;
            if (rpp < 0) {
                rpp += m;
            }

            r = rp;
            rp = rpp;
        }

        return modulo.mod(r);
    }
}
