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
 * {@code gcdInverse} メソッドの転送先を扱う. <br>
 * 実装を補助する役目.
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

        int r = modulo.mod(a);
        int rp = m;

        // m >= 2
        // u, up はmod m の世界で正規化されている
        int u = 1;
        int up = 0;
        while (rp != 0) {
            int q = r / rp;

            int rpp = r - q * rp;
            r = rp;
            rp = rpp;

            // upp = (u - q*up) mod m を計算する.
            int mod_q_up = modulo.modpr(q, up);
            int upp = u - mod_q_up;
            if (upp < 0) {
                upp += m;
            }

            u = up;
            up = upp;
        }

        return modulo.mod(u);
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

        long r = modulo.mod(a);
        long rp = m;

        // m >= 2
        // u, up はmod m の世界で正規化されている
        long u = 1;
        long up = 0;
        while (rp != 0) {
            long q = r / rp;

            long rpp = r - q * rp;
            r = rp;
            rp = rpp;

            // upp = (u - q*up) mod m を計算する.
            long mod_q_up = modulo.modpr(q, up);
            long upp = u - mod_q_up;
            if (upp < 0L) {
                upp += m;
            }

            u = up;
            up = upp;
        }

        return modulo.mod(u);
    }

    public static void main(String[] args) {
        int m = 12;
        ModuloInt modM = ModuloInt.get(m);

        int a = 8;
        System.out.println(modM.gcdInverse(a));
    }
}
