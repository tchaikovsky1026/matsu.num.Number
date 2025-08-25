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

import matsu.num.number.ModuloLong;
import matsu.num.number.primes.modulo.SimplePrimeModuloFactory;

/**
 * {@code long} 型の素数を法とするモジュロ演算を行うインターフェース.
 * 
 * <p>
 * 法となる除数 (divisor) は必ず素数である. <br>
 * このドキュメント上では, 除数の値を <i>p</i> と表す.
 * </p>
 * 
 * <p>
 * このインターフェースの実装クラスは, イミュータブルかつスレッドセーフであることが保証されている. <br>
 * 最も基本的なインスタンスの取得方法は, {@link #get(long)} メソッドをコールすることである.
 * </p>
 * 
 * <p>
 * {@link PrimeModuloInt} インターフェースに, 簡単な専門的説明が用意されている.
 * </p>
 * 
 * @implSpec
 *               このインターフェースは実装を隠ぺいして型を公開するためのものである. <br>
 *               モジュール外で継承・実装してはいけない.
 * 
 * @author Matsuura Y.
 */
public interface PrimeModuloLong extends ModuloLong {

    /**
     * 1 &le; <i>a</i> &lt; <i>p</i> を満たす整数 <i>a</i> について,
     * mod&nbsp;<i>p</i> に対する位数 <i>k</i> を返す. <br>
     * 1 &le; <i>k</i> &lt; <i>p</i> である.
     * 
     * @param a 数
     * @return mod&nbsp;<i>p</i> に対する <i>a</i> の位数
     * @throws IllegalArgumentException
     *             1 &le; <i>a</i> &lt; <i>p</i> でない場合
     */
    public abstract long order(long a);

    /**
     * 1 &le; <i>a</i> &lt; <i>p</i> を満たす整数 <i>a</i> について,
     * <i>a</i><i>r</i> &equiv; 1 (mod&nbsp;<i>p</i>)
     * を満たす整数 <i>r</i> を返す (逆元). <br>
     * 1 &le; <i>r</i> &lt; <i>p</i> である. <br>
     * このような <i>r</i> は唯一であり,
     * <i>a</i><sup><i>p</i> - 2</sup> mod&nbsp;<i>p</i>
     * に一致する.
     * 
     * @param a 数
     * @return mod&nbsp;<i>p</i> に対する <i>a</i> の逆元
     * @throws IllegalArgumentException
     *             1 &le; <i>a</i> &lt; <i>p</i> でない場合
     */
    public abstract long inverse(long a);

    /**
     * 1 &le; <i>a</i> &lt; <i>p</i> を満たす整数 <i>a</i> が
     * mod&nbsp;<i>p</i> に対する原始根かどうか (位数が <i>p</i> - 1 かどうか) を判定する.
     * 
     * @param a 数
     * @return <i>a</i> がmod&nbsp;<i>p</i> に対する原始根である場合は {@code true}
     * @throws IllegalArgumentException
     *             1 &le; <i>a</i> &lt; <i>p</i> でない場合
     */
    public abstract boolean isPrimitiveRoot(long a);

    /**
     * mod&nbsp;<i>p</i> に対する原始根 <i>a</i> のうちの1つを返す. <br>
     * 1 &le; <i>a</i> &lt; <i>p</i> である.
     * 
     * @return mod&nbsp;<i>p</i> に対する原始根
     */
    public abstract long primitiveRoot();

    /**
     * {@code long} 型の素数 <i>p</i> について,
     * <i>p</i> を法とするモジュロ演算を返す.
     * 
     * <p>
     * <i>p</i> は素数でなければならない.
     * </p>
     * 
     * @param p 除数 <i>p</i>
     * @return <i>p</i> を法とするモジュロ演算
     * @throws IllegalArgumentException <i>p</i> が素数でない場合
     */
    public static PrimeModuloLong get(long p) {
        return SimplePrimeModuloFactory.createFrom(ModuloLong.get(p));
    }
}
