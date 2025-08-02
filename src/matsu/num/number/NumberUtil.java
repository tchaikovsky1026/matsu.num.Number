/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.2
 */
package matsu.num.number;

/**
 * 整数型に関する, 簡単な処理を行うユーティリティ.
 * 
 * @author Matsuura Y.
 */
final class NumberUtil {

    private NumberUtil() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /**
     * 32bit整数 <i>x</i>, <i>y</i> について,
     * <i>x</i><i>y</i> を符号付き64bitで計算した結果を返す.
     * 
     * @param x <i>x</i>
     * @param y <i>y</i>
     * @return <i>x</i><i>y</i> (符号付き)
     */
    public static long multiplyFull(int x, int y) {
        return (long) x * y;
    }

    /**
     * 32bit整数 <i>x</i>, <i>y</i> について,
     * <i>x</i><i>y</i> を符号付き64bitで計算したときの上位32bitを計算する.
     * 
     * <p>
     * 下位32bitを計算するメソッド {@code multiplyLow} は提供していない. <br>
     * 下位32bitは {@code int} の通常の積を {@code int} 型で受け取ることで得られる.
     * </p>
     * 
     * @param x <i>x</i>
     * @param y <i>y</i>
     * @return <i>x</i><i>y</i> (符号付き) の上位32bit
     */
    public static int multiplyHigh(int x, int y) {
        return (int) (multiplyFull(x, y) >> 32);
    }

    /**
     * 32bit整数 <i>x</i>, <i>y</i> について,
     * <i>x</i><i>y</i> を符号無し64bitで計算した結果を返す.
     * 
     * @param x <i>x</i>
     * @param y <i>y</i>
     * @return <i>x</i><i>y</i> (符号付き)
     */
    public static long unsignedMultiplyFull(int x, int y) {
        return (x & 0xFFFF_FFFFL) * (y & 0xFFFF_FFFFL);
    }

    /**
     * 32bit整数 <i>x</i>, <i>y</i> について,
     * <i>x</i><i>y</i> を符号無し64bitで計算したときの上位32bitを計算する.
     * 
     * <p>
     * 下位32bitを計算するメソッド {@code unsignedMultiplyLow} は提供していない. <br>
     * 下位32bitは {@code int} の通常の積を {@code int} 型で受け取ることで得られる.
     * </p>
     * 
     * @param x <i>x</i>
     * @param y <i>y</i>
     * @return <i>x</i><i>y</i> (符号無し) の上位32bit
     */
    public static int unsignedMultiplyHigh(int x, int y) {
        return (int) (unsignedMultiplyFull(x, y) >>> 32);
    }

    /**
     * 64bit整数 <i>x</i>, <i>y</i> について,
     * <i>x</i><i>y</i> を符号付き128bitで計算したときの上位64bitを計算する.
     * 
     * <p>
     * 下位64bitを計算するメソッド {@code multiplyLowLong} は提供していない. <br>
     * 下位64bitは {@code long} の通常の積を {@code long} 型で受け取ることで得られる.
     * </p>
     * 
     * @param x <i>x</i>
     * @param y <i>y</i>
     * @return <i>x</i><i>y</i> (符号付き) の上位64bit
     */
    public static long multiplyHighLong(long x, long y) {

        /*
         * x,yを符号あり64bit整数と見なし,
         * x = x1 * (2^32) + x2,
         * y = y1 * (2^32) + y2
         * となる, x1, x2, y1, y2 を計算する.
         * ただし, x1, y1は符号あり32bit (-2^31 以上 2^31 - 1 以下),
         * x2, y2は符号なし32bit (0 以上 2^32 - 1 以下) である.
         */
        long x1 = x >> 32;
        long x2 = x & 0xFFFF_FFFFL;
        long y1 = y >> 32;
        long y2 = y & 0xFFFF_FFFFL;

        /*
         * x*y = (x1*y1)*(2^64) + (x1*y2 + x2*y1)*(2^32) + (x2*y2)
         * である.
         * ここで, (x2*y2) だけは, 符号なし64bitと見なす必要がある.
         * 下32bitは (x2*y2) からしか生じないので, 無視してよい (符号なしに注意して, [>>> 32]).
         * 
         * 符号あり32bitと符号なし32bitの積は,
         * -(2^63) 以上, 2^63 - 1 - 2^32 以下である.
         * よって, x1*y2 や x2*y2 に 2^32 以下の値を加えても, オーバーフローを起こさない.
         */
        long t = x1 * y2 + ((x2 * y2) >>> 32);
        // tを上位, 下位bitに分割
        long t1 = t >> 32;
        long t2 = t & 0xFFFF_FFFFL;

        return x1 * y1 + t1 + ((t2 + x2 * y1) >> 32);
    }

    /**
     * 64bit整数 <i>x</i>, <i>y</i> について,
     * <i>x</i><i>y</i> を符号無し128bitで計算したときの上位64bitを計算する.
     * 
     * <p>
     * 下位64bitを計算するメソッド {@code unsignedMultiplyLowLong} は提供していない. <br>
     * 下位64bitは {@code long} の通常の積を {@code long} 型で受け取ることで得られる.
     * </p>
     * 
     * @param x <i>x</i>
     * @param y <i>y</i>
     * @return <i>x</i><i>y</i> (符号無し) の上位64bit
     */
    public static long unsignedMultiplyHighLong(long x, long y) {

        /*
         * 符号なし整数どうしの和・差・積は, 下位bitに関しては符号ありと同一の結果となる.
         */

        /*
         * x,yを符号なし64bit整数と見なし,
         * x = x1 * (2^32) + x2,
         * y = y1 * (2^32) + y2
         * となる, x1, x2, y1, y2 を計算する.
         * x1, x2, y1, y2は 0 以上 2^32 - 1 以下である.
         */
        long x1 = x >>> 32;
        long x2 = x & 0xFFFF_FFFFL;
        long y1 = y >>> 32;
        long y2 = y & 0xFFFF_FFFFL;

        /*
         * 上位bitを得るには, 64bitの情報である(x1*y1)などを素朴には128bitに変換し,
         * 下位bitを0埋め, 上位bitをして和を計算すればよい.
         * 下位32bitはx2 * y2 からしか生じないので繰り上がりが起こらず, 無視してよい.
         * 
         * 符号なし32bitの積は必ず, 2^64 - 1 - 2^32 以下である.
         * よって, 積に 2^32 以下の値を加えても, オーバーフローを起こさない.
         */
        long t = x1 * y2 + ((x2 * y2) >>> 32);
        // tを上位, 下位bitに分割
        long t1 = t >>> 32;
        long t2 = t & 0xFFFF_FFFFL;

        return x1 * y1 + t1 + ((t2 + x2 * y1) >>> 32);
    }
}
