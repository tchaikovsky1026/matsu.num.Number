/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.20
 */
package matsu.num.number.primes.modulo;

import matsu.num.number.ModuloLong;
import matsu.num.number.primes.Primality;
import matsu.num.number.primes.PrimeModuloLong;

/**
 * {@link PrimeModuloLong} の骨格実装. <br>
 * 主に, メソッド契約のための実装提供と, {@link ModuloLong} メソッドの実装の提供である.
 * 
 * @author Matsuura Y.
 */
abstract class SkeletalPrimeModuloLong implements PrimeModuloLong {

    private final ModuloLong modulo;

    /**
     * 唯一のコンストラクタ. <br>
     * mod p 演算を実現するモジュロ演算を与えて, インスタンスを構築する.
     * 
     * @param modulo mod p を実現するモジュロ演算
     * @throws IllegalArgumentException p が素数でない場合
     * @throws NullPointerException 引数がnullの場合
     */
    SkeletalPrimeModuloLong(ModuloLong modulo) {
        super();
        if (!Primality.isPrime(modulo.divisor())) {
            throw new IllegalArgumentException("divisor is not prime: p = " + modulo.divisor());
        }
        this.modulo = modulo;
    }

    @Override
    public final long divisor() {
        return this.modulo.divisor();
    }

    @Override
    public final long mod(long x) {
        return this.modulo.mod(x);
    }

    @Override
    public final long modpr(long x, long y) {
        return this.modulo.modpr(x, y);
    }

    @Override
    public final long modpr(long... x) {
        return this.modulo.modpr(x);
    }

    @Override
    public final long modpow(long x, long k) {
        return this.modulo.modpow(x, k);
    }

    @Override
    public final long order(long a) {
        validateDividend(a);

        return this.orderConcrete(a);
    }

    @Override
    public final boolean isPrimitiveRoot(long a) {
        validateDividend(a);

        return this.isPrimitiveRootConcrete(a);
    }

    /**
     * a が {@literal 1 <= a <= p - 1} を満たすかどうかを判定する.
     * 
     * @param a a
     * @throws IllegalArgumentException {@literal 1 <= a <= p - 1} を満たさない場合
     */
    private void validateDividend(long a) {
        if (!(1L <= a && a < this.divisor())) {
            throw new IllegalArgumentException(
                    "illegal: not 1 <= a <= p-1: a = " + a + ", p = " + this.divisor());
        }
    }

    /**
     * {@link #order(long)} の具体的処理を実装する抽象メソッド.
     * 
     * <p>
     * 外部から {@link #order(long)} を呼んだとき, 引数が正当かどうか
     * (1 以上 <i>p</i> - 1 以下かどうか)
     * が判定され, 正当な場合はこのメソッドがコールされる. <br>
     * このメソッド内で例外をスローしてはいけない. <br>
     * このメソッドを継承先から直接コールすることは, ほとんどの場合不適切である.
     * </p>
     * 
     * @implSpec
     *               アクセスレベルを継承先で緩和してはいけない.
     * 
     * @param a 位数を計算する整数, 1 以上 <i>p</i> - 1 以下が確定
     * @return mod&nbsp;<i>p</i> に対する位数
     */
    abstract long orderConcrete(long a);

    /**
     * {@link #isPrimitiveRoot(long)} の具体的処理を実装する抽象メソッド.
     * 
     * <p>
     * 外部から {@link #isPrimitiveRoot(long)} を呼んだとき, 引数が正当かどうか
     * (1 以上 <i>p</i> - 1 以下かどうか)
     * が判定され, 正当な場合はこのメソッドがコールされる. <br>
     * このメソッド内で例外をスローしてはいけない. <br>
     * このメソッドを継承先から直接コールすることは, ほとんどの場合不適切である.
     * </p>
     * 
     * @implSpec
     *               アクセスレベルを継承先で緩和してはいけない.
     * 
     * @param a 原始根かどうかを判定する整数, 1 以上 <i>p</i> - 1 以下が確定
     * @return 原始根である場合は {@code true}
     */
    abstract boolean isPrimitiveRootConcrete(long a);

    /**
     * このインスタンスの文字列表現を返す.
     * 
     * <p>
     * 文字列表現は明確に規定されておらず, バージョン間の互換性も担保されていない. <br>
     * おそらく次のような形式だろう. <br>
     * {@code PrimeModulo(long, divisor = %divisor)}
     * </p>
     */
    @Override
    public String toString() {
        return "PrimeModulo(long, divisor = %s)"
                .formatted(this.divisor());
    }
}
