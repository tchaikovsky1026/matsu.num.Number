/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.7.5
 */
package matsu.num.number.modlong;

import matsu.num.number.ModuloLong;

/**
 * {@code long} で扱える範囲, かつ 2 の累乗でない偶数を divisor とする
 * {@code long} 型に関するモジュロ演算.
 * 
 * @author Matsuura Y.
 */
final class ModuloEvenNotPow2 extends SkeletalModuloLong {

    private final long divisor;

    /*
     * 基本方針:
     * m を 3 以上の奇数として,
     * x mod (2^d * m) を, x mod 2^d と x mod m から計算する.
     * l = 2^d とする. l と m は互いに素である.
     * 
     * 中国剰余定理により, (l,m) が互いに素の場合,
     * 任意の r, s に対して
     * x = r (mod m)
     * x = s (mod l)
     * となるような x は法 lm について一意に存在する.
     * この x の値は次のように求まる.
     * 
     * 今, r, s は正規化済み (0 以上 divisor 未満) であるとする.
     * x = t1 + m*t2 (0 <= t1 < m, 0 <= t2 < l)
     * とおくと, 法lmについて t1, t2 は一意であり, t1 = r は直ちにわかる.
     * 次に, 法 l に対する m の乗法逆元を m^(-1) とすると,
     * t2 = [(s - r) * m^(-1)] mod l
     * となる.
     * 
     * 
     * mod 2^d と mod m の計算, 2^d を法とする m の逆元の計算は,
     * 他のクラスに依存する.
     */

    private final ModuloLong modPow2Calculator;
    private final ModuloLong modMCalculator;

    /** mod 2^d を計算するためのマスク. */
    // x mod 2^d は, x & (2^d-1) に等しい.
    private final long modPow2BitMask;

    private final long minv;
    private final long m;

    /**
     * d と m を与えて, 2^d * m を法としたモジュロ演算を構築する.
     * 
     * <p>
     * d は 1 以上, m は 3 以上の奇数でなければならない. <br>
     * 当然, 2^d * m の値は扱える値の範囲内でなければならない.
     * 引数のバリデーションは行われていないので,
     * 呼び出しもとでチェックすること.
     * </p>
     * 
     * @param pow2Exponent d
     * @param innerDivisor m
     */
    ModuloEvenNotPow2(int pow2Exponent, long innerDivisor) {
        super();

        assert pow2Exponent >= 1 && (innerDivisor & 1L) == 1L && innerDivisor != 1L : "not: divisor = 2^d * m";

        this.divisor = innerDivisor << pow2Exponent;
        this.m = innerDivisor;
        this.modPow2Calculator = new ModuloPow2(pow2Exponent);
        this.modMCalculator = new ModuloMontgomery(innerDivisor);

        this.modPow2BitMask = (1L << pow2Exponent) - 1;
        this.minv = ModPow2InverseUtil.invModR(innerDivisor) & this.modPow2BitMask;
    }

    @Override
    public long divisor() {
        return this.divisor;
    }

    @Override
    public long mod(long x) {

        if (0 <= x && x < this.divisor) {
            return x;
        }

        long modM = modMCalculator.mod(x);
        long modPow2 = modPow2Calculator.mod(x);

        return this.combinedMod(modM, modPow2);
    }

    @Override
    public long modpr(long x, long y) {
        long modM = modMCalculator.modpr(x, y);
        long modPow2 = modPow2Calculator.modpr(x, y);

        return this.combinedMod(modM, modPow2);
    }

    @Override
    public long modpr(long... x) {
        long modM = modMCalculator.modpr(x);
        long modPow2 = modPow2Calculator.modpr(x);

        return this.combinedMod(modM, modPow2);
    }

    @Override
    long modpowConcrete(long x, long k) {
        long modM = modMCalculator.modpow(x, k);
        long modPow2 = modPow2Calculator.modpow(x, k);

        return this.combinedMod(modM, modPow2);
    }

    /**
     * mod m と mod 2^d の値を与えて, mod (m * (2^d)) を計算する. <br>
     * 引数は正規化されていなければならない.
     * 
     * @param modMRemainder mod m
     * @param modPow2Remainder mod 2^d
     * @return mod (m * (2^d))
     */
    private long combinedMod(long modMRemainder, long modPow2Remainder) {

        /*
         * l = 2^d とする.
         * 
         * x = r (mod m) と x = s (mod l) を満たす r, s を与えたときの,
         * x mod (rs) を返す.
         * 
         * r, s が正規化されているとき,
         * t1 = r, t2 = [(s - r) * m^(-1)] mod l
         * として, t1 + m*t2 が求める値である.
         */

        return modMRemainder + m * (((modPow2Remainder - modMRemainder) * minv) & modPow2BitMask);
    }
}
