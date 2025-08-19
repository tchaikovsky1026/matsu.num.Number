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

import java.util.Arrays;

/**
 * 2 以上の {@code long} 型整数 <i>n</i> の素因数を表すクラス. <br>
 * <i>n</i> の値に基づく equality と comparability を提供する.
 * 
 * <p>
 * このクラスは素因数分解の結果を返すための型であり, ユーザーがインスタンスを生成する手段は提供しない.
 * </p>
 * 
 * @author Matsuura Y.
 */
public final class PrimeFactorLong implements Comparable<PrimeFactorLong> {

    private final long original;
    private final long[] factors;

    /**
     * 唯一の非公開コンストラクタ.
     * 
     * <p>
     * 引数のバリデーションは行われていないので, 呼び出しもとでチェックすること.
     * </p>
     * 
     * @param original 素因数分解前の値: 2以上の整数
     * @param factors 素因数分解結果: 素因数が昇順に並び, 総積がoriginalに一致
     */
    PrimeFactorLong(long original, long[] factors) {
        super();

        this.original = original;
        this.factors = factors;
    }

    /**
     * 素因数分解前の元の値 <i>n</i> を返す.
     * 
     * @return <i>n</i>
     */
    public final long original() {
        return original;
    }

    /**
     * <i>n</i> の素因数を配列として返す.
     * 
     * <p>
     * 配列は長さ 1 以上であり,
     * 昇順にソートされている. <br>
     * また, 要素の総積は <i>n</i> に一致する.
     * </p>
     * 
     * @return 素因数
     */
    public final long[] factors() {
        return factors.clone();
    }

    /**
     * インスタンスの等価性を判定する. <br>
     * 比較の仕様はクラス説明文の通りである.
     */
    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof PrimeFactorLong target)) {
            return false;
        }

        return this.original == target.original;
    }

    /**
     * インスタンスのハッシュコードを返す.
     */
    @Override
    public final int hashCode() {
        int result = 1;
        result = 31 * result + Long.hashCode(this.original);
        return result;
    }

    /**
     * インスタンスの文字列表現を返す.
     * 
     * <p>
     * 文字列表現は明確には規定されておらず, バージョン間の互換も担保されていない. <br>
     * おそらく次のような表現だろう. <br>
     * {@code PrimeFactors(long, original = %original, factors = %factors)}
     * </p>
     */
    @Override
    public String toString() {
        return "PrimeFactors(long, original = %s, factors = %s)"
                .formatted(this.original(), Arrays.toString(this.factors()));
    }

    /**
     * インスタンスを比較する. <br>
     * 比較の仕様はクラス説明文の通りである.
     */
    @Override
    public int compareTo(PrimeFactorLong o) {
        return Long.compare(this.original, o.original);
    }
}
