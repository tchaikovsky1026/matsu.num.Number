/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.20
 */
package matsu.num.number.primes;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 2 以上の {@code int} 型整数 <i>n</i> の素因数を表すクラス. <br>
 * <i>n</i> の値に基づく equality と comparability を提供する.
 * 
 * <p>
 * このクラスは素因数分解の結果を返すための型であり, ユーザーがインスタンスを生成する手段は提供しない.
 * </p>
 * 
 * @author Matsuura Y.
 * @see PrimeFactorize
 */
public final class PrimeFactorInt implements Comparable<PrimeFactorInt> {

    private final int original;
    private final SortedMap<Integer, Integer> factor2Number;

    // 遅延初期化ロック用オブジェクト
    private final Object lock = new Object();

    // 遅延初期化される
    private volatile int[] factors;

    /**
     * 唯一の非公開コンストラクタ.
     * 
     * <p>
     * 引数のバリデーションは行われていないので, 呼び出しもとでチェックすること.
     * </p>
     * 
     * @param original 素因数分解前の値: 2以上の整数
     * @param factorsList 素因数分解結果: 総積がoriginalに一致
     */
    PrimeFactorInt(int original, Collection<Integer> factorsList) {
        this(original, Factors2MapHolder.factors2Map(factorsList));

        this.factors = factorsList.stream()
                .mapToInt(i -> i.intValue())
                .toArray();
        Arrays.sort(this.factors);
    }

    /**
     * 素因数のストリームを "素因数とその個数のマップ" に変換する機能.
     */
    private static final class Factors2MapHolder {

        private static final Collector<Integer, ?, SortedMap<Integer, Integer>> factors2MapCollector;

        static {
            Collector<Object, ?, Integer> counting = Collectors.collectingAndThen(
                    Collectors.counting(),
                    (Long i) -> Integer.valueOf(i.intValue()));

            factors2MapCollector = Collectors.groupingBy(
                    i -> i, TreeMap<Integer, Integer>::new, counting);
        }

        /**
         * エンクロージングクラスからはこのメソッドを呼ぶ.
         */
        static SortedMap<Integer, Integer> factors2Map(Collection<Integer> factorsList) {
            return factorsList.stream()
                    .collect(Factors2MapHolder.factors2MapCollector);
        }
    }

    /**
     * 内部から呼ばれる.
     * 
     * <p>
     * 引数のバリデーションは行われていないので, 呼び出しもとでチェックすること. <br>
     * パッケージに対しても非公開であり, 強力な契約を持つ.
     * </p>
     * 
     * @param original 素因数分解前の値: 2以上の整数
     * @param factor2Number 素因数とその個数のマップ:
     *            自然順序のcompare,
     *            Value は1以上,
     *            マップへの参照は外部に漏れていない
     */
    private PrimeFactorInt(int original, SortedMap<Integer, Integer> factor2Number) {
        super();

        this.original = original;
        this.factor2Number = factor2Number;
    }

    /**
     * 素因数分解前の元の値 <i>n</i> を返す.
     * 
     * @return <i>n</i>
     */
    public final int original() {
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
    public final int[] factors() {
        int[] out = this.factors;
        if (Objects.nonNull(out)) {
            return out.clone();
        }

        synchronized (lock) {
            out = this.factors;
            if (Objects.nonNull(out)) {
                return out.clone();
            }

            out = this.factor2Number.entrySet().stream()
                    .flatMapToInt(
                            // Entry(素因数, 繰り返し) を Stream(素因数,素因数,...)に変換
                            e -> IntStream
                                    .iterate(
                                            e.getKey().intValue(), IntUnaryOperator.identity())
                                    .limit(e.getValue().intValue()))
                    .toArray();
            this.factors = out.clone();
            return out;
        }
    }

    /**
     * インスタンスが等価かどうか判定する. <br>
     * equality の仕様はクラス説明文の通りである.
     */
    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof PrimeFactorInt target)) {
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
        result = 31 * result + Integer.hashCode(this.original);
        return result;
    }

    /**
     * インスタンスの文字列表現を返す.
     * 
     * <p>
     * 文字列表現は明確には規定されておらず, バージョン間の互換も担保されていない. <br>
     * おそらく次のような表現だろう. <br>
     * {@code PrimeFactors(int, original = %original, factors = %factors)}
     * </p>
     */
    @Override
    public final String toString() {
        return "PrimeFactors(int, original = %s, factors = %s)"
                .formatted(this.original(), Arrays.toString(this.factors()));
    }

    /**
     * インスタンスを比較する. <br>
     * comparability の仕様はクラス説明文の通りである.
     */
    @Override
    public final int compareTo(PrimeFactorInt o) {
        return Integer.compare(this.original, o.original);
    }
}
