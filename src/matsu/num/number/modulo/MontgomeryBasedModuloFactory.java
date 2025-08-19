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

import matsu.num.number.ModuloInt;
import matsu.num.number.ModuloLong;

/**
 * Montgomery modular multiplication をもとに構築されるモジュロ演算のファクトリ.
 * 
 * @author Matsuura Y.
 */
public final class MontgomeryBasedModuloFactory {

    private MontgomeryBasedModuloFactory() {
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
                    ? Mod1Int.INSTANCE
                    : new MontgomeryInt(divisor);
        }

        int pow2Exponent = Integer.numberOfTrailingZeros(divisor);
        int innerDivisor = divisor >> pow2Exponent;
        return innerDivisor == 1
                ? new ModuloIntPow2(pow2Exponent)
                : new EvenNotPow2ModuloInt(pow2Exponent, innerDivisor);
    }

    /**
     * {@code long} 型整数について,
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
    public static ModuloLong get(long divisor) {
        if (divisor <= 0L) {
            throw new IllegalArgumentException("illegal: divisor <= 0");
        }

        if ((divisor & 1L) == 1L) {
            return divisor == 1L
                    ? Mod1Long.INSTANCE
                    : new MontgomeryLong(divisor);
        }

        int pow2Exponent = Long.numberOfTrailingZeros(divisor);
        long innerDivisor = divisor >> pow2Exponent;
        return innerDivisor == 1L
                ? new ModuloLongPow2(pow2Exponent)
                : new EvenNotPow2ModuloLong(pow2Exponent, innerDivisor);
    }
}
