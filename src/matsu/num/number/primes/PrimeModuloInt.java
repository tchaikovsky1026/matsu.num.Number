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
import matsu.num.number.primes.modulo.SimplePrimeModuloFactory;

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
 * 
 * <hr>
 * 
 * <h2>素数を法とするモジュロ演算</h2>
 * 
 * <p>
 * 一般に, <i>m</i> を法とするモジュロ演算において,
 * 与えられた整数 <i>a</i> に対して
 * <i>a</i> <sup><i>k</i></sup> &equiv; 1
 * となる 正整数 <i>k</i> が存在するとき,
 * そのような <i>k</i> の最小値を <i>a</i> の<b>位数</b>という
 * (存在しないときは無限として扱う). <br>
 * <i>a</i> <sup><i>k</i></sup> &equiv; 1 が成立するとき,
 * <i>a</i> の位数は必ず <i>k</i> の約数である. <br>
 * 法が素数であるとき
 * (<i>m</i> = <i>p</i>),
 * Fermat の小定理により 1 &le; <i>a</i> &lt; <i>p</i> なる <i>a</i> に対して
 * <i>a</i><sup><i>p</i> - 1</sup> &equiv; 1
 * であるので,
 * そのような <i>a</i> の位数は有限である.
 * </p>
 * 
 * <p>
 * 素数 <i>p</i> を法とするとき,
 * 位数が <i>p</i> - 1 であるような <i>a</i> が必ず存在する
 * (証明は少々複雑). <br>
 * このような <i>a</i> を mod&nbsp;<i>p</i> に対する<b>原始根</b>という. <br>
 * <i>a</i> が原始根であるとき,
 * <i>a</i><sup>1</sup>, <i>a</i><sup>2</sup>, ...,
 * <i>a</i><sup><i>p</i> - 1</sup> は mod&nbsp;<i>p</i> について相異なる.
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
     * 1 &le; <i>a</i> &lt; <i>p</i> を満たす整数 <i>a</i> について,
     * mod&nbsp;<i>p</i> に対する位数 <i>k</i> を返す. <br>
     * 1 &le; <i>k</i> &lt; <i>p</i> である.
     * 
     * @param a 数
     * @return mod&nbsp;<i>p</i> に対する <i>a</i> の位数
     * @throws IllegalArgumentException
     *             1 &le; <i>a</i> &lt; <i>p</i> でない場合
     */
    public abstract int order(int a);

    /**
     * 1 &le; <i>a</i> &lt; <i>p</i> を満たす整数 <i>a</i> について,
     * <i>a</i><i>r</i> &equiv; 1 (mod&nbsp;<i>p</i>)
     * を満たす整数 <i>r</i> を返す (乗法逆元). <br>
     * 1 &le; <i>r</i> &lt; <i>p</i> である. <br>
     * このような <i>r</i> は唯一であり,
     * <i>a</i><sup><i>p</i> - 2</sup> mod&nbsp;<i>p</i>
     * に一致する.
     * 
     * @param a 数
     * @return mod&nbsp;<i>p</i> に対する <i>a</i> の乗法逆元
     * @throws IllegalArgumentException
     *             1 &le; <i>a</i> &lt; <i>p</i> でない場合
     */
    public abstract int inverse(int a);

    /**
     * 1 &le; <i>a</i> &lt; <i>p</i> を満たす整数 <i>a</i> が
     * mod&nbsp;<i>p</i> に対する原始根かどうか (位数が <i>p</i> - 1 かどうか) を判定する.
     * 
     * @param a 数
     * @return <i>a</i> がmod&nbsp;<i>p</i> に対する原始根である場合は {@code true}
     * @throws IllegalArgumentException
     *             1 &le; <i>a</i> &lt; <i>p</i> でない場合
     */
    public abstract boolean isPrimitiveRoot(int a);

    /**
     * mod&nbsp;<i>p</i> に対する原始根 <i>a</i> のうちの1つを返す. <br>
     * 1 &le; <i>a</i> &lt; <i>p</i> である.
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
     * @throws IllegalArgumentException <i>p</i> が素数でない場合
     */
    public static PrimeModuloInt get(int p) {
        return SimplePrimeModuloFactory.createFrom(ModuloInt.get(p));
    }
}
