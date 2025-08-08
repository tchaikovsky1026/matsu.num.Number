/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.8
 */
package matsu.num.number.modulo;

/**
 * Montgomery modular multiplication をベースとした,
 * {@code int} 型に関するモジュロ演算. <br>
 * 2の累乗でない偶数を除数としたもので扱う.
 * 
 * @author Matsuura Y.
 */
final class EvenNotPow2ModulusInt extends SkeletalModulusInt {

    private final int divisor;

    private final ModulusInt modPow2Calculator;
    private final ModulusInt modMCalculator;

    /**
     * 2^s - 1
     */
    private final int modPow2BitMask;

    private final int minv;
    private final int m;

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
    EvenNotPow2ModulusInt(int pow2Exponent, int innerDivisor) {
        super();

        assert pow2Exponent >= 1 && (innerDivisor & 1) == 1 && innerDivisor != 1 : "not: divisor = 2^d * m";

        this.divisor = innerDivisor << pow2Exponent;
        this.m = innerDivisor;
        this.modPow2Calculator = new ModulusIntPow2(pow2Exponent);
        this.modMCalculator = new MontgomeryInt(innerDivisor);

        this.modPow2BitMask = (1 << pow2Exponent) - 1;
        this.minv = InverseModPow2.invModR(innerDivisor) & this.modPow2BitMask;
    }

    @Override
    public int divisor() {
        return this.divisor;
    }

    @Override
    public int mod(int x) {

        if (0 <= x && x < this.divisor) {
            return x;
        }

        int modM = modMCalculator.mod(x);
        int modPow2 = modPow2Calculator.mod(x);

        return this.combinedMod(modM, modPow2);
    }

    @Override
    public int modpr(int x, int y) {
        int modM = modMCalculator.modpr(x, y);
        int modPow2 = modPow2Calculator.modpr(x, y);

        return this.combinedMod(modM, modPow2);
    }

    @Override
    public int modpr(int... x) {
        int modM = modMCalculator.modpr(x);
        int modPow2 = modPow2Calculator.modpr(x);

        return this.combinedMod(modM, modPow2);
    }

    @Override
    public int modpow(int x, int k) {
        int modM = modMCalculator.modpow(x, k);
        int modPow2 = modPow2Calculator.modpow(x, k);

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
    private int combinedMod(int modM, int modPow2) {
        return modM + m * (((modPow2 - modM) * minv) & modPow2BitMask);
    }
}
