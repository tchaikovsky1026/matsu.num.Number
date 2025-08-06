/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.6
 */
package matsu.num.number.incubator.modulo;

/**
 * {@code int} 型の値のモジュロ演算を行うインターフェース
 * (インキュベータ用).
 * 
 * <p>
 * 除数 (divisor) は必ず正整数でなければならない.
 * </p>
 * 
 * @author Matsuura Y.
 */
interface ModulusInt {

    /**
     * このインスタンスの除数の値を返す.
     * 
     * @return 除数
     */
    public abstract int divisor();

    /**
     * x mod m を返す
     * (m は除数).
     * 
     * @param x x
     * @return n mod m
     */
    public abstract int mod(int x);

    /**
     * x*y mod m を返す
     * (m は除数).
     * 
     * @param x x
     * @param y y
     * @return x*y mod m
     */
    public int modpr(int x, int y);

    /**
     * x1*x2*... mod m を返す
     * (m は除数).
     * 
     * <p>
     * lengthが0の場合は1が返る.
     * </p>
     * 
     * @param x x
     * @return x1*x2*... mod m
     * @throws NullPointerException 引数がnullの場合
     */
    public int modpr(int... x);

    /**
     * x^k mod m を返す
     * (m は除数).
     * 
     * <p>
     * 指数は 0 以上でなければならない. <br>
     * x = 0, k = 0 の場合は1が返る.
     * </p>
     * 
     * @param x x
     * @param k k
     * @return x^k mod m
     * @throws IllegalArgumentException 指数が0未満の場合
     */
    public int modpow(int x, int k);
}
