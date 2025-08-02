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
 * {@code long} 型を経由せずにビット操作のみで行う {@link Bit32Multiplier}.
 * 
 * @author Matsuura Y.
 */
final class Bit32MultiplierByBitOperator implements Bit32Multiplier {

    /**
     * 唯一のコンストラクタ.
     */
    Bit32MultiplierByBitOperator() {
        super();
    }

    @Override
    public int multiplyHigh(int x, int y) {
        int x1 = x >> 16;
        int x2 = x & 0xFFFF;
        int y1 = y >> 16;
        int y2 = y & 0xFFFF;

        int z2 = x2 * y2;
        int t = x1 * y2 + (z2 >>> 16);
        int z1 = t & 0xFFFF;
        int z0 = t >> 16;
        z1 += x2 * y1;

        return x1 * y1 + z0 + (z1 >> 16);
    }

    @Override
    public int unsignedMultiplyHigh(int x, int y) {
        int x1 = x >>> 16;
        int x2 = x & 0xFFFF;
        int y1 = y >>> 16;
        int y2 = y & 0xFFFF;

        int z2 = x2 * y2;
        int t = x1 * y2 + (z2 >>> 16);
        int z1 = t & 0xFFFF;
        int z0 = t >>> 16;
        z1 += x2 * y1;

        return x1 * y1 + z0 + (z1 >>> 16);
    }
}
