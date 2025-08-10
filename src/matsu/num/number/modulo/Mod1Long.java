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

import java.util.Objects;

/**
 * 除数1に対する {@link ModulusLong} の実装.
 * 
 * @author Matsuura Y.
 */
final class Mod1Long extends SkeletalModulusLong {

    /**
     * シングルトン.
     */
    static final ModulusLong INSTANCE = new Mod1Long();

    /**
     * 内部から呼ばれる.
     */
    private Mod1Long() {
        super();
    }

    @Override
    public long divisor() {
        return 1L;
    }

    @Override
    public long mod(long x) {
        return 0L;
    }

    @Override
    public long modpr(long x, long y) {
        return 0L;
    }

    @Override
    public long modpr(long... x) {
        Objects.requireNonNull(x);
        return 0L;
    }

    @Override
    public long modpow(long x, long k) {
        if (k < 0L) {
            throw new IllegalArgumentException("illegal: exponent k is negative: k = " + k);
        }
        return 0L;
    }
}
