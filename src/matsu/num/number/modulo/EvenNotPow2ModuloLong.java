/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.10
 */
package matsu.num.number.modulo;

/**
 * Montgomery modular multiplication をベースとした,
 * {@code long} 型に関するモジュロ演算. <br>
 * 2の累乗でない偶数を除数としたもので扱う.
 * 
 * @author Matsuura Y.
 */
final class EvenNotPow2ModuloLong extends SkeletalModuloLong {

    private final long divisor;

    private final ModuloLong modPow2Calculator;
    private final ModuloLong modMCalculator;

    /**
     * 2^s - 1
     */
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
    EvenNotPow2ModuloLong(int pow2Exponent, long innerDivisor) {
        super();

        assert pow2Exponent >= 1 && (innerDivisor & 1L) == 1L && innerDivisor != 1L : "not: divisor = 2^d * m";

        this.divisor = innerDivisor << pow2Exponent;
        this.m = innerDivisor;
        this.modPow2Calculator = new ModuloLongPow2(pow2Exponent);
        this.modMCalculator = new MontgomeryLong(innerDivisor);

        this.modPow2BitMask = (1L << pow2Exponent) - 1;
        this.minv = InverseModPow2.invModR(innerDivisor) & this.modPow2BitMask;
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
    public long modpow(long x, long k) {
        long modM = modMCalculator.modpow(x, k);
        long modPow2 = modPow2Calculator.modpow(x, k);

        return this.combinedMod(modM, modPow2);
    }

    /**
     * mod m と mod 2^s を与えて, mod ((2^s)*m) を計算する. <br>
     * 引数は正規化されていなければならない.
     * 
     * @param modM mod m
     * @param modPow2 mod 2^s
     * @return mod ((2^s)*m)
     */
    private long combinedMod(long modM, long modPow2) {
        return modM + m * (((modPow2 - modM) * minv) & modPow2BitMask);
    }
}
