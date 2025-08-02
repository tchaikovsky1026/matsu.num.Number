/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.2
 */
package matsu.num.number.incubator;

/**
 * {@code long} 型を経由して行う {@link Bit32Multiplier}.
 * 
 * @author Matsuura Y.
 */
final class Bit32MultiplierByLong implements Bit32Multiplier {

    /**
     * 唯一のコンストラクタ.
     */
    Bit32MultiplierByLong() {
        super();
    }

    @Override
    public int multiplyHigh(int x, int y) {
        return (int) (((long) x * y) >> 32);
    }

    @Override
    public int unsignedMultiplyHigh(int x, int y) {
        return (int) ((Integer.toUnsignedLong(x) * Integer.toUnsignedLong(y)) >>> 32);
    }
}
