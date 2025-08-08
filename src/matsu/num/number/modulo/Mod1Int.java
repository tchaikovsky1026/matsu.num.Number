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

import java.util.Objects;

/**
 * 除数1に対する {@link ModulusInt} の実装.
 * 
 * @author Matsuura Y.
 */
final class Mod1Int implements ModulusInt {

    /**
     * シングルトン.
     */
    static final ModulusInt INSTANCE = new Mod1Int();

    /**
     * 内部から呼ばれる.
     */
    private Mod1Int() {
        super();
    }

    @Override
    public int divisor() {
        return 1;
    }

    @Override
    public int mod(int x) {
        return 0;
    }

    @Override
    public int modpr(int x, int y) {
        return 0;
    }

    @Override
    public int modpr(int... x) {
        Objects.requireNonNull(x);
        return 0;
    }

    @Override
    public int modpow(int x, int k) {
        if (k < 0) {
            throw new IllegalArgumentException("illegal: exponent k is negative: k = " + k);
        }
        return 0;
    }
}
