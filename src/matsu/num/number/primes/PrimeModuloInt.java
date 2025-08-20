/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.19
 */
package matsu.num.number.primes;

import matsu.num.number.ModuloInt;

/**
 * {@code int} 型の素数を法とするモジュロ演算を行うインターフェース.
 * 
 * <p>
 * 法となる除数 (divisor) は必ず素数である. <br>
 * このドキュメント上では, 除数の値を <i>p</i> と表す.
 * </p>
 * 
 * <p>
 * このインターフェースの実装クラスは, イミュータブルかつスレッドセーフであることが保証されている. <br>
 * 最も基本的なインスタンスの取得方法は, {@link #get(int)} メソッドをコールすることである.
 * </p>
 * 
 * @implSpec
 *               このインターフェースは実装を隠ぺいして型を公開するためのものである. <br>
 *               モジュール外で継承・実装してはいけない.
 * 
 * @author Matsuura Y.
 */
public interface PrimeModuloInt extends ModuloInt {

    /**
     * 1 以上 <i>p</i> - 1 以下の整数 <i>a</i> について,
     * mod&nbsp;<i>p</i> に対する位数を返す.
     * 
     * @param a 位数を計算する整数
     * @return mod&nbsp;<i>p</i> に対する位数
     * @throws IllegalArgumentException <i>a</i> が 1 以上 <i>p</i> - 1 以下でない場合
     */
    public abstract int order(int a);

    /**
     * 1 以上 <i>p</i> - 1 以下の整数 <i>a</i> について,
     * mod&nbsp;<i>p</i> に対する原始根かどうか (位数が <i>p</i> - 1 かどうか) を判定する.
     * 
     * @param a 原始根かどうかを判定する整数
     * @return 原始根である場合は {@code true}
     * @throws IllegalArgumentException <i>a</i> が 1 以上 <i>p</i> - 1 以下でない場合
     */
    public abstract boolean isPrimitiveRoot(int a);

    /**
     * mod&nbsp;<i>p</i> に対する原始根のうちの1つを返す. <br>
     * 戻り値は 1 以上 <i>p</i> - 1 以下である.
     * 
     * @return mod&nbsp;<i>p</i> に対する原始根
     */
    public abstract int primitiveRoot();

    /**
     * {@code int} 型の素数 <i>p</i> について,
     * <i>p</i> を法とするモジュロ演算を返す.
     * 
     * <p>
     * <i>p</i> は素数でなければならない.
     * </p>
     * 
     * @param p 除数 <i>p</i>
     * @return <i>p</i> を法とするモジュロ演算
     * @throws IllegalArgumentException 引数が素数でない場合
     * @deprecated throw new AssertionError("TODO")
     */
    @Deprecated
    public static PrimeModuloInt get(int p) {
        throw new AssertionError("TODO");
    }
}
