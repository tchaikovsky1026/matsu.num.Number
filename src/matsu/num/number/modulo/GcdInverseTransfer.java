/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.25
 */
package matsu.num.number.modulo;

import matsu.num.number.ModuloInt;
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
 * <p>
 * {@literal m >= 2} について
 * ar = gcd(a,m) (mod m)
 * なる r を求めるには, 拡張 Euclid の互除法を用いればよい. <br>
 * gcd の計算に用いる Euclid の互除法はオーバーフローの心配がないが,
 * この拡張 Euclid の互除法での r の更新にはその保証がなく,
 * 毎回の更新でモジュラ正規化を行うことが望ましい. <br>
 * したがって, Stein のアルゴリズムの高速性の恩恵を受けにくいのみならず,
 * r の更新には「2と互いに素でビットシフトしても結果が変わらない」という性質が使えないので,
 * まったく無意味である. <br>
 * そこで, 拡張 Euclid の互除法を素朴に実行する.
 * </p>
 * 
 * <p>
 * v1 = a, v2 = m, r1 = 1, r2 = 0
 * とすると,
 * a*r1 = v1 (mod m), a*r2 = v2 (mod m) が成立する. <br>
 * (v1,v2,r1,r2)についてこの性質を満たしているとき,
 * r = r1 + q*r2, v = v1 + q*v2
 * とすれば,
 * a*r = v (mod m)
 * を満たす. <br>
 * よって, v の更新を Euclid の互除法により行い,
 * その時使用した q を r の更新に用いれば,
 * v = gcd(a,m) となった時点での r が求める値である. <br>
 * (ただし, r は一意ではない)
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
