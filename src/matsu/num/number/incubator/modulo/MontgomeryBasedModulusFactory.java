/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.8
 */
package matsu.num.number.incubator.modulo;

/**
 * Montgomery modular multiplication をもとに構築されるモジュロ演算のファクトリ.
 * 
 * <p>
 * インキュベータであるが, 公開できる可能性がある.
 * </p>
 * 
 * @author Matsuura Y.
 */
final class MontgomeryBasedModulusFactory {

    private MontgomeryBasedModulusFactory() {
        // インスタンス化不可
        throw new AssertionError();
    }

    static ModulusInt get(int divisor) {
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
                ? new ModulusIntPow2(pow2Exponent)
                : new EvenNotPow2NumberModulusInt(pow2Exponent, innerDivisor);
    }
}
