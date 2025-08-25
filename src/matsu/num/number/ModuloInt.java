/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.15
 */
package matsu.num.number;

import matsu.num.number.modulo.MontgomeryBasedModuloFactory;

/**
 * {@code int} 型の値のモジュロ演算を行うインターフェース.
 * 
 * <p>
 * 除数 (divisor) は必ず正整数でなければならない. <br>
 * このドキュメント上では, 除数の値を <i>m</i> と表す.
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
public interface ModuloInt {

    /**
     * このインスタンスの除数 <i>m</i> の値を返す.
     * 
     * @return 除数 <i>m</i>
     */
    public abstract int divisor();

    /**
     * 整数 <i>x</i> に対する <i>m</i> を法とする剰余 <i>r</i> を返す. <br>
     * 0 &le; <i>r</i> &lt; <i>m</i> である.
     * 
     * @param x 数
     * @return <i>x</i> mod&nbsp;<i>m</i>
     */
    public abstract int mod(int x);

    /**
     * 整数の積 <i>x</i><i>y</i> に対する <i>m</i> を法とする剰余 <i>r</i> を返す. <br>
     * 0 &le; <i>r</i> &lt; <i>m</i> である.
     * 
     * @param x 数1
     * @param y 数2
     * @return <i>x</i><i>y</i> mod&nbsp;<i>m</i>
     */
    public int modpr(int x, int y);

    /**
     * 整数の総積
     * <i>x</i><sub>1</sub><i>x</i><sub>2</sub>&middot;&middot;&middot;<i>x</i><sub><i>n</i></sub>
     * に対する
     * <i>m</i> を法とする剰余 <i>r</i> を返す. <br>
     * 0 &le; <i>r</i> &lt; <i>m</i> である.
     * 
     * <p>
     * 引数が空の場合は 1 mod&nbsp;<i>m</i> を返す.
     * </p>
     * 
     * @param x <i>x</i><sub>1</sub>, <i>x</i><sub>2</sub>,
     *            ..., <i>x</i><sub><i>n</i></sub>
     * @return (<i>x</i><sub>1</sub><i>x</i><sub>2</sub>&middot;&middot;&middot;<i>x</i><sub><i>n</i></sub>)
     *             mod&nbsp;<i>m</i>
     * @throws NullPointerException 引数がnullの場合
     */
    public int modpr(int... x);

    /**
     * 整数の累乗 <i>x</i><sup><i>k</i></sup> に対する
     * <i>m</i> を法とする剰余 <i>r</i> を返す. <br>
     * 0 &le; <i>r</i> &lt; <i>m</i> である.
     * 
     * <p>
     * <i>k</i> &ge; 0 でなければならない. <br>
     * <i>k</i> = 0 の場合は
     * (<i>x</i> = 0 であっても)
     * 1 mod&nbsp;<i>m</i>
     * を返す.
     * </p>
     * 
     * @param x 底
     * @param k 指数
     * @return <i>x</i><sup><i>k</i></sup> mod&nbsp;<i>m</i>
     * @throws IllegalArgumentException <i>k</i> &lt; 0 の場合
     */
    public int modpow(int x, int k);

    /**
     * 整数 <i>a</i> に対する
     * <i>a</i><i>r</i> &equiv; gcd(<i>a</i>, <i>m</i>) (mod&nbsp;<i>m</i>)
     * を満たす整数 <i>r</i> のうちの1つを返す (GCD逆元). <br>
     * 0 &le; <i>r</i> &lt; <i>m</i> である.
     * 
     * <p>
     * <i>a</i>, <i>m</i> に対して,
     * <i>a</i><i>r</i> + <i>m</i><i>s</i> = gcd(<i>a</i>, <i>m</i>)
     * を満たす <i>r</i>, <i>s</i> の組は必ず存在する
     * (B&eacute;zout's identity). <br>
     * <i>r</i> に着目すると,
     * <i>a</i><i>r</i> &equiv; gcd(<i>a</i>, <i>m</i>)
     * (mod&nbsp;<i>m</i>)
     * である. <br>
     * B&eacute;zout's identity を満たす <i>r</i> は
     * mod&nbsp;(<i>m</i> / gcd(<i>a</i>, <i>m</i>))
     * で一意であるので,
     * mod&nbsp;<i>m</i> においては複数候補がある.
     * </p>
     * 
     * <p>
     * このメソッドの戻り値 <i>r</i> を使い, gcd(<i>a</i>, <i>m</i>) を
     * <i>a</i><i>r</i> mod&nbsp;<i>m</i>
     * により得ることができる ({@link #modpr(int, int) modpr(a,r)}). <br>
     * ただし, gcd(<i>a</i>, <i>m</i>) を得ることのみが目的であれば,
     * {@link Gcd#gcd(int, int)} の方が適切である.
     * </p>
     * 
     * @param a 数
     * @return GCD逆元,
     *             <i>a</i><i>r</i> &equiv; gcd(<i>a</i>, <i>m</i>)
     *             を満たす <i>r</i>
     */
    public int gcdInverse(int a);

    /**
     * {@code int} 型整数 <i>m</i> について,
     * <i>m</i> を法とするモジュロ演算を返す.
     * 
     * <p>
     * <i>m</i> &gt; 0 でなければならない.
     * </p>
     * 
     * @param divisor 除数 <i>m</i>
     * @return <i>m</i> を法とするモジュロ演算
     * @throws IllegalArgumentException <i>m</i> &le; 0 の場合
     */
    public static ModuloInt get(int divisor) {
        return MontgomeryBasedModuloFactory.get(divisor);
    }
}
