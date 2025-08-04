/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.7.31
 */
package matsu.num.number.modulo;

/**
 * {@code int} 型の値のモジュロ演算を行うインターフェース.
 * 
 * <p>
 * 除数 (divisor) は必ず正整数でなければならない. <br>
 * このドキュメント上では, 除数の値を <i>m</i> と表す.
 * </p>
 * 
 * @implSpec
 *               このインターフェースは実装を隠ぺいして型を公開するためのものである. <br>
 *               モジュール外で継承・実装してはいけない.
 * 
 * @author Matsuura Y.
 */
interface ModulusInt {

    /**
     * このインスタンスの除数 <i>m</i> の値を返す.
     * 
     * @return 除数 <i>m</i>
     */
    public abstract int divisor();

    /**
     * <i>m</i> を法とする剰余を計算する. <br>
     * 剰余は 0 以上 <i>m</i> 未満の値である.
     * 
     * @param x 被除数
     * @return <i>x</i> mod <i>m</i>
     */
    public abstract int mod(int x);

    /**
     * <i>m</i> を法とする, 積の剰余を計算する. <br>
     * 剰余は 0 以上 <i>m</i> 未満の値である.
     * 
     * @param x 数1
     * @param y 数2
     * @return <i>x</i><i>y</i> mod <i>m</i>
     */
    public int modpr(int x, int y);

    /**
     * 与えられた数
     * <i>x</i><sub>1</sub>, <i>x</i><sub>2</sub>,
     * ..., <i>x</i><sub><i>n</i></sub> について,
     * <i>m</i> を法とする総積の剰余を計算する. <br>
     * 剰余は 0 以上 <i>m</i> 未満の値である.
     * 
     * @param x <i>x</i><sub>1</sub>, <i>x</i><sub>2</sub>,
     *            ..., <i>x</i><sub><i>n</i></sub>
     * @return (<i>x</i><sub>1</sub><i>x</i><sub>2</sub>&middot;&middot;&middot;<i>x</i><sub><i>n</i></sub>)
     *             mod <i>m</i>
     */
    public int modpr(int... x);

    /**
     * <i>m</i> を法とする, 累乗の剰余を計算する. <br>
     * 剰余は 0 以上 <i>m</i> 未満の値である.
     * 
     * <p>
     * 指数は 0 以上でなければならない.
     * </p>
     * 
     * @param x 底
     * @param k 指数
     * @return <i>x</i><sup><i>k</i></sup> mod <i>m</i>
     * @throws IllegalArgumentException 指数が0未満の場合
     */
    public int modpow(int x, int k);
}
