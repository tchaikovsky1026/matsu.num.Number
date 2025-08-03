/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.3
 */
package matsu.num.number.incubator;

/**
 * 32bit整数に関する積を扱う.
 * 
 * @author Matsuura Y.
 */
interface Bit32Multiplier {

    /**
     * 32bit整数 x, y について,
     * x * y を符号付き64bitで計算する.
     * 
     * @param x x
     * @param y y
     * @return x * y (符号付き) の64bit
     */
    public abstract long multiplyFull(int x, int y);

    /**
     * 32bit整数 x, y について,
     * x * y を符号付き64bitで計算したときの上位32bitを計算する.
     * 
     * @param x x
     * @param y y
     * @return x * y (符号付き) の上位32bit
     */
    public abstract int multiplyHigh(int x, int y);

    /**
     * 32bit整数 x, y について,
     * x * y を符号無し64bitで計算する.
     * 
     * @param x x
     * @param y y
     * @return x * y (符号無し) の64bit
     */
    public abstract long unsignedMultiplyFull(int x, int y);

    /**
     * 32bit整数 x, y について,
     * x * y を符号無し64bitで計算したときの上位32bitを計算する.
     * 
     * @param x x
     * @param y y
     * @return x * y (符号無し) の上位32bit
     */
    public abstract int unsignedMultiplyHigh(int x, int y);
}
