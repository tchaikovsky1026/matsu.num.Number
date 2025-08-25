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

import matsu.num.number.ModuloInt;
import matsu.num.number.primes.Primality;
import matsu.num.number.primes.PrimeModuloInt;

/**
 * {@link PrimeModuloInt} の骨格実装. <br>
 * 主に, メソッド契約のための実装提供と, {@link ModuloInt} メソッドの実装の提供である.
 * 
 * @author Matsuura Y.
 */
abstract class SkeletalPrimeModuloInt implements PrimeModuloInt {

    private final ModuloInt modulo;

    /**
     * 唯一のコンストラクタ. <br>
     * mod p 演算を実現するモジュロ演算を与えて, インスタンスを構築する.
     * 
     * @param modulo mod p を実現するモジュロ演算
     * @throws IllegalArgumentException p が素数でない場合
     * @throws NullPointerException 引数がnullの場合
     */
    SkeletalPrimeModuloInt(ModuloInt modulo) {
        super();
        if (!Primality.isPrime(modulo.divisor())) {
            throw new IllegalArgumentException("divisor is not prime: p = " + modulo.divisor());
        }
        this.modulo = modulo;
    }

    @Override
    public final int divisor() {
        return this.modulo.divisor();
    }

    @Override
    public final int mod(int x) {
        return this.modulo.mod(x);
    }

    @Override
    public final int modpr(int x, int y) {
        return this.modulo.modpr(x, y);
    }

    @Override
    public final int modpr(int... x) {
        return this.modulo.modpr(x);
    }

    @Override
    public final int modpow(int x, int k) {
        return this.modulo.modpow(x, k);
    }

    @Override
    public final int gcdInverse(int a) {
        return this.modulo.gcdInverse(a);
    }

    @Override
    public final int order(int a) {
        validateDividend(a);

        return this.orderConcrete(a);
    }

    @Override
    public final boolean isPrimitiveRoot(int a) {
        validateDividend(a);

        return this.isPrimitiveRootConcrete(a);
    }

    /**
     * a が {@literal 1 <= a <= p - 1} を満たすかどうかを判定する.
     * 
     * @param a a
     * @throws IllegalArgumentException {@literal 1 <= a <= p - 1} を満たさない場合
     */
    private void validateDividend(int a) {
        if (!(1 <= a && a < this.divisor())) {
            throw new IllegalArgumentException(
                    "illegal: not 1 <= a <= p-1: a = " + a + ", p = " + this.divisor());
        }
    }

    /**
     * {@link #order(int)} の具体的処理を実装する抽象メソッド.
     * 
     * <p>
     * 外部から {@link #order(int)} を呼んだとき, 引数が正当かどうか
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
    abstract int orderConcrete(int a);

    /**
     * {@link #isPrimitiveRoot(int)} の具体的処理を実装する抽象メソッド.
     * 
     * <p>
     * 外部から {@link #isPrimitiveRoot(int)} を呼んだとき, 引数が正当かどうか
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
    abstract boolean isPrimitiveRootConcrete(int a);

    /**
     * このインスタンスの文字列表現を返す.
     * 
     * <p>
     * 文字列表現は明確に規定されておらず, バージョン間の互換性も担保されていない. <br>
     * おそらく次のような形式だろう. <br>
     * {@code PrimeModulo(int, divisor = %divisor)}
     * </p>
     */
    @Override
    public String toString() {
        return "PrimeModulo(int, divisor = %s)"
                .formatted(this.divisor());
    }
}
