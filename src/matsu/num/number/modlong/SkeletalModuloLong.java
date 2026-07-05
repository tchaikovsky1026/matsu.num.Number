/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.7.5
 */
package matsu.num.number.modlong;

import matsu.num.number.ModuloLong;

/**
 * {@link ModuloLong} の骨格実装. <br>
 * このパッケージで扱うすべての {@link ModuloLong} の実装は, このクラスを継承するべきである.
 * 
 * @author Matsuura Y.
 */
abstract class SkeletalModuloLong implements ModuloLong {

    /** 唯一のコンストラクタ. */
    SkeletalModuloLong() {
        super();
    }

    /**
     * @implSpec
     *               処理は {@link GcdInverseTransfer} に転送されている. <br>
     *               この転送は, 安全かつ実用的な実装である. <br>
     *               継承先でさらに効率的な実装が提供できる場合は, オーバーライドしても良い.
     */
    @Override
    public long gcdInverse(long a) {
        return GcdInverseTransfer.gcdInverse(a, this);
    }

    /** @throws IllegalArgumentException {@inheritDoc} */
    @Override
    public long modpow(long x, long k) {
        if (k < 0) {
            throw new IllegalArgumentException("illegal: exponent k is negative: k = " + k);
        }
        return modpowConcrete(x, k);
    }

    /**
     * {@link #modpow(long, long)} の具体的計算を実行する抽象メソッド.
     * 
     * <p>
     * {@link ModuloLong#modpow(long, long) ModuloLong.modpow} メソッドが持つ
     * {@literal k < 0} での例外スローがこのクラスに実装されており,
     * この抽象メソッドはそれを通過した後に呼ばれる. <br>
     * すなわち, {@literal k >= 0} が確定している.
     * </p>
     * 
     * @implSpec
     *               引数バリデーションが完了しているため, 例外をスローしてはならない.
     * 
     * @param x 底
     * @param k 指数, {@literal k >= 0} が確定
     * @return <i>x</i><sup><i>k</i></sup> mod&nbsp;<i>m</i>
     */
    abstract long modpowConcrete(long x, long k);

    /**
     * このインスタンスの文字列表現を返す.
     * 
     * <p>
     * 文字列表現は明確に規定されておらず, バージョン間の互換性も担保されていない. <br>
     * おそらく次のような形式だろう. <br>
     * {@code %InterfaceName(divisor = %divisor) }
     * </p>
     */
    @Override
    public String toString() {
        return "%s(divisor = %s)"
                .formatted(ModuloLong.class.getSimpleName(), this.divisor());
    }
}
