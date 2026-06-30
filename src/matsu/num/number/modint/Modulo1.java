/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.7.1
 */
package matsu.num.number.modint;

import java.util.Objects;

import matsu.num.number.ModuloInt;

/**
 * 除数1に対する {@link ModuloInt} の実装.
 * 
 * <p>
 * mod 1 ではあらゆる dividend に対して常に 0 を返す.
 * </p>
 * 
 * @author Matsuura Y.
 */
final class Modulo1 extends SkeletalModuloInt {

    /** このクラスの シングルトンインスタンス. */
    static final ModuloInt INSTANCE = new Modulo1();

    /** 非公開コンストラクタ. 内部から呼ばれる. */
    private Modulo1() {
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
    int modpowConcrete(int x, int k) {
        return 0;
    }
}
