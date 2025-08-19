/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.19
 */
package matsu.num.number;

import matsu.num.number.modulo.MontgomeryBasedModuloFactory;

/**
 * {@code long} 型の値のモジュロ演算を行うインターフェース.
 * 
 * <p>
 * 除数 (divisor) は必ず正整数でなければならない. <br>
 * このドキュメント上では, 除数の値を <i>m</i> と表す.
 * </p>
 * 
 * <p>
 * このインターフェースの実装クラスは, イミュータブルかつスレッドセーフであることが保証されている. <br>
 * 最も基本的なインスタンスの取得方法は, {@link #get(long)} メソッドをコールすることである.
 * </p>
 * 
 * @implSpec
 *               このインターフェースは実装を隠ぺいして型を公開するためのものである. <br>
 *               モジュール外で継承・実装してはいけない.
 * 
 * @author Matsuura Y.
 */
public interface ModuloLong {

    /**
     * このインスタンスの除数 <i>m</i> の値を返す.
     * 
     * @return 除数 <i>m</i>
     */
    public abstract long divisor();

    /**
     * <i>m</i> を法とする剰余を計算する. <br>
     * 剰余は 0 以上 <i>m</i> 未満の値である.
     * 
     * @param x 被除数
     * @return <i>x</i> mod <i>m</i>
     */
    public abstract long mod(long x);

    /**
     * <i>m</i> を法とする, 積の剰余を計算する. <br>
     * 剰余は 0 以上 <i>m</i> 未満の値である.
     * 
     * @param x 数1
     * @param y 数2
     * @return <i>x</i><i>y</i> mod <i>m</i>
     */
    public long modpr(long x, long y);

    /**
     * 与えられた数
     * <i>x</i><sub>1</sub>, <i>x</i><sub>2</sub>,
     * ..., <i>x</i><sub><i>n</i></sub> について,
     * <i>m</i> を法とする総積の剰余を計算する. <br>
     * 剰余は 0 以上 <i>m</i> 未満の値である.
     * 
     * <p>
     * 引数が空の場合は 1 mod <i>m</i> を返す.
     * </p>
     * 
     * @param x <i>x</i><sub>1</sub>, <i>x</i><sub>2</sub>,
     *            ..., <i>x</i><sub><i>n</i></sub>
     * @return (<i>x</i><sub>1</sub><i>x</i><sub>2</sub>&middot;&middot;&middot;<i>x</i><sub><i>n</i></sub>)
     *             mod <i>m</i>
     */
    public long modpr(long... x);

    /**
     * <i>m</i> を法とする, 累乗 (<i>x</i><sup><i>k</i></sup>) の剰余を計算する. <br>
     * 剰余は 0 以上 <i>m</i> 未満の値である.
     * 
     * <p>
     * 指数 <i>k</i> は 0 以上でなければならない. <br>
     * <i>k</i> = 0 の場合は
     * (<i>x</i> = 0 であっても)
     * 1 mod <i>m</i> を返す.
     * </p>
     * 
     * @param x 底
     * @param k 指数
     * @return <i>x</i><sup><i>k</i></sup> mod <i>m</i>
     * @throws IllegalArgumentException 指数が0未満の場合
     */
    public long modpow(long x, long k);

    /**
     * {@code long} 型整数 <i>m</i> について,
     * <i>m</i> を法とするモジュロ演算を返す.
     * 
     * <p>
     * 除数 <i>m</i> は正でなければならない.
     * </p>
     * 
     * @param divisor 除数 <i>m</i>
     * @return <i>m</i> を法とするモジュロ演算
     * @throws IllegalArgumentException 引数が正の整数でない場合
     */
    public static ModuloLong get(long divisor) {
        return MontgomeryBasedModuloFactory.get(divisor);
    }
}
