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
 * <p>
 * このクラスは, {@link Bit32Multiplier} の実装のプロトタイプを提供するだけでなく,
 * より大きな整数型を経由せずに上位bitを計算する方法を検証するのにも用いる. <br>
 * {@code long} 型の積の上位bitの計算に応用可能.
 * </p>
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
    public long multiplyFull(int x, int y) {

        /*
         * x,yを符号あり32bit整数と見なし,
         * x = x1 * (2^16) + x2,
         * y = y1 * (2^16) + y2
         * となる, x1, x2, y1, y2 を計算する.
         * ただし, x1, y1は符号あり16bit (-2^16 以上 2^16 - 1 以下),
         * x2, y2は符号なし16bit (0 以上 2^16 - 1 以下) である.
         */
        int x1 = x >> 16;
        int x2 = x & 0xFFFF;
        int y1 = y >> 16;
        int y2 = y & 0xFFFF;

        /*
         * x*y = (x1*y1)*(2^32) + (x1*y2 + x2*y1)*(2^16) + (x2*y2)
         * である.
         * ここで, (x2*y2) だけは, 符号なし32bitと見なす.
         * 
         * 符号あり16bitと符号なし16bitの積は,
         * -(2^31) 以上, 2^31 - 1 - 2^16 以下である.
         * よって, x1*y2 や x2*y2 に 2^16 以下の値を加えても, オーバーフローを起こさない.
         */
        int r = x2 * y2;
        int r1 = r >>> 16;

        int t = x1 * y2 + r1;
        int t1 = t >> 16;
        int t2 = t & 0xFFFF;

        int s = t2 + x2 * y1;

        int high = x1 * y1 + t1 + (s >> 16);
        int low = (s << 16) | (r & 0xFFFF);

        return (((long) high) << 32) | (low & 0xFFFF_FFFFL);
    }

    @Override
    public int multiplyHigh(int x, int y) {
        /*
         * multiplyFullに従う.
         */
        int x1 = x >> 16;
        int x2 = x & 0xFFFF;
        int y1 = y >> 16;
        int y2 = y & 0xFFFF;

        int r = x2 * y2;
        int r1 = r >>> 16;

        int t = x1 * y2 + r1;
        int t1 = t >> 16;
        int t2 = t & 0xFFFF;

        int s = t2 + x2 * y1;

        return x1 * y1 + t1 + (s >> 16);
    }

    @Override
    public long unsignedMultiplyFull(int x, int y) {

        /*
         * 符号なし整数どうしの和・差・積は, 下位bitに関しては符号ありと同一の結果となる.
         */

        /*
         * x,yを符号なし32bit整数と見なし,
         * x = x1 * (2^16) + x2,
         * y = y1 * (2^16) + y2
         * となる, x1, x2, y1, y2 を計算する.
         * ただし, x1, x2, y1, y2は符号なし16bit (0 以上 2^16 - 1 以下) である.
         */
        int x1 = x >>> 16;
        int x2 = x & 0xFFFF;
        int y1 = y >>> 16;
        int y2 = y & 0xFFFF;

        /*
         * x*y = (x1*y1)*(2^32) + (x1*y2 + x2*y1)*(2^16) + (x2*y2)
         * である (積はすべて符号なし32bit).
         * 
         * 符号なし16bitどうしの積は,
         * 2^32 - 1 - 2^16 以下である.
         * よって, x1*y2 や x2*y2 に 2^16 以下の値を加えても, オーバーフローを起こさない.
         */
        int r = x2 * y2;
        int r1 = r >>> 16;

        int t = x1 * y2 + r1;
        int t1 = t >>> 16;
        int t2 = t & 0xFFFF;

        int s = t2 + x2 * y1;

        int high = x1 * y1 + t1 + (s >>> 16);
        int low = (s << 16) | (r & 0xFFFF);

        return (((long) high) << 32) | (low & 0xFFFF_FFFFL);
    }

    @Override
    public int unsignedMultiplyHigh(int x, int y) {

        /*
         * unsignedMultiplyFullに従う.
         */
        int x1 = x >>> 16;
        int x2 = x & 0xFFFF;
        int y1 = y >>> 16;
        int y2 = y & 0xFFFF;

        int r = x2 * y2;
        int r1 = r >>> 16;

        int t = x1 * y2 + r1;
        int t1 = t >>> 16;
        int t2 = t & 0xFFFF;

        int s = t2 + x2 * y1;

        return x1 * y1 + t1 + (s >>> 16);
    }
}
