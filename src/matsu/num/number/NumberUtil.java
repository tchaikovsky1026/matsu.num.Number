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
}
