/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.7.10
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

    /*
     * 方針:
     * divisor = 2^d * m とし,
     * mod 2^d と mod m から mod divisor を得る.
     */

    private final long divisor;

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
        return modMRemainder + m * (((modPow2Remainder - modMRemainder) * minv) & modPow2BitMask);
    }
}
