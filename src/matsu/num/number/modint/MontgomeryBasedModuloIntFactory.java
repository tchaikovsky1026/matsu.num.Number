/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.8
 */
package matsu.num.number.modint;

import matsu.num.number.ModuloInt;

/**
 * Montgomery modular multiplication をもとに構築されるモジュロ演算のファクトリ.
 * 
 * @author Matsuura Y.
 */
public final class MontgomeryBasedModuloIntFactory {

    private MontgomeryBasedModuloIntFactory() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /**
     * {@code int} 型整数について,
     * 与えた正の整数を除数とするモジュロ演算を返す.
     * 
     * <p>
     * 引数の値は正でなければならない.
     * </p>
     * 
     * @param divisor 除数
     * @return 除数に対応するモジュロ演算
     * @throws IllegalArgumentException 引数が正の整数でない場合
     */
    public static ModuloInt get(int divisor) {
        if (divisor <= 0) {
            throw new IllegalArgumentException("illegal: divisor <= 0");
        }

        if ((divisor & 1) == 1) {
            return divisor == 1
                    ? Modulo1.INSTANCE
                    : new ModuloMontgomery(divisor);
        }

        int pow2Exponent = Integer.numberOfTrailingZeros(divisor);
        int innerDivisor = divisor >> pow2Exponent;
        return innerDivisor == 1
                ? new ModuloPow2(pow2Exponent)
                : new ModuloEvenNotPow2(pow2Exponent, innerDivisor);
    }
}
