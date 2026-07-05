/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.10
 */
package matsu.num.number.modlong;

import java.util.Objects;

import matsu.num.number.ModuloLong;

/**
 * 除数1に対する {@link ModuloLong} の実装.
 * 
 * @author Matsuura Y.
 */
final class Modulo1 extends SkeletalModuloLong {

    /**
     * シングルトン.
     */
    static final ModuloLong INSTANCE = new Modulo1();

    /**
     * 内部から呼ばれる.
     */
    private Modulo1() {
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
    long modpowConcrete(long x, long k) {
        return 0L;
    }
}
