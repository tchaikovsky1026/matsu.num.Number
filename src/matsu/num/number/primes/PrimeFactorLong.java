/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.21
 */
package matsu.num.number.primes;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.LongUnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * 1 以上の {@code long} 型整数 <i>n</i> の素因数を表すクラス. <br>
 * <i>n</i> の値に基づく equality と comparability を提供する.
 * 
 * <p>
 * このクラスは素因数分解の結果を返すための型であり, ユーザーがインスタンスを生成する手段は提供しない.
 * </p>
 * 
 * @author Matsuura Y.
 * @see PrimeFactorize
 */
public final class PrimeFactorLong implements Comparable<PrimeFactorLong> {

    private final long original;
    private final SortedMap<Long, Integer> factor2Number;
    private final boolean prime;

    // 遅延初期化ロック用オブジェクト
    private final Object lock = new Object();

    // 遅延初期化される
    private volatile long[] factors;

    /**
     * 唯一の非公開コンストラクタ.
     * 
     * <p>
     * 引数のバリデーションは行われていないので, 呼び出しもとでチェックすること.
     * </p>
     * 
     * @param original 素因数分解前の値: 1以上の整数
     * @param factors 素因数分解結果: 総積がoriginalに一致
     */
    PrimeFactorLong(long original, Collection<Long> factorsList) {
        this(original, Factors2MapHolder.factors2Map(factorsList));

        this.factors = factorsList.stream()
                .mapToLong(i -> i.longValue())
                .toArray();
        Arrays.sort(this.factors);
    }

    /**
     * 素因数のストリームを "素因数とその個数のマップ" に変換する機能.
     */
    private static final class Factors2MapHolder {

        private static final Collector<Long, ?, SortedMap<Long, Integer>> factors2MapCollector;

        static {
            Collector<Object, ?, Integer> counting = Collectors.collectingAndThen(
                    Collectors.counting(),
                    (Long i) -> Integer.valueOf(i.intValue()));

            factors2MapCollector = Collectors.groupingBy(
                    i -> i, TreeMap<Long, Integer>::new, counting);
        }

        /**
         * エンクロージングクラスからはこのメソッドを呼ぶ.
         */
        static SortedMap<Long, Integer> factors2Map(Collection<Long> factorsList) {
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
     * @param original 素因数分解前の値: 1以上の整数
     * @param factor2Number 素因数とその個数のマップ:
     *            自然順序のcompare,
     *            Value は1以上,
     *            マップへの参照は外部に漏れていない
     */
    private PrimeFactorLong(long original, SortedMap<Long, Integer> factor2Number) {
        super();

        this.original = original;
        this.factor2Number = factor2Number;
        this.prime = this.factor2Number.containsKey(Long.valueOf(original));
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
     * 元の値 <i>n</i> が素数かどうかを返す.
     * 
     * @return <i>n</i> が素数の場合は {@code true}
     */
    public final boolean isPrime() {
        return prime;
    }

    /**
     * <i>n</i> の素因数を配列として返す.
     * 
     * <p>
     * 配列は昇順にソートされている. <br>
     * また, 要素の総積は <i>n</i> に一致する. <br>
     * <i>n</i> = 1 の場合は空配列である.
     * </p>
     * 
     * @return 素因数
     */
    public final long[] factors() {
        long[] out = this.factors;
        if (Objects.nonNull(out)) {
            return out.clone();
        }

        synchronized (lock) {
            out = this.factors;
            if (Objects.nonNull(out)) {
                return out.clone();
            }

            out = this.factor2Number.entrySet().stream()
                    .flatMapToLong(
                            // Entry(素因数, 繰り返し) を Stream(素因数,素因数,...)に変換
                            e -> LongStream
                                    .iterate(
                                            e.getKey().longValue(), LongUnaryOperator.identity())
                                    .limit(e.getValue().intValue()))
                    .toArray();
            this.factors = out.clone();
            return out;
        }
    }

    /**
     * {@code this} の素因数分解前の値 <i>n</i> に対し,
     * <i>n</i> の素因数 <i>q</i> で除した,
     * <i>n</i>/<i>q</i> の素因数分解を返す.
     * 
     * <p>
     * <i>q</i> が <i>n</i> の素因数でない場合は空が返る.
     * </p>
     * 
     * @param q <i>q</i>, 素因数の1つ
     * @return <i>n</i>/<i>q</i> に対する素因数分解, <i>q</i> が不適の場合は空
     */
    public final Optional<PrimeFactorLong> dividedBy(long q) {
        return this.factor2Number.containsKey(Long.valueOf(q))
                ? Optional.of(this.dividedByConcrete(q))
                : Optional.empty();
    }

    /**
     * クラスの内部で使用する. <br>
     * {@code this} の素因数分解前の値 <i>n</i> に対し,
     * <i>n</i> の素因数 <i>q</i> で除した,
     * <i>n</i>/<i>q</i> の素因数分解を返す.
     * 
     * <p>
     * q を素因数に含んでいなければならない
     * (バリデーションはされていない).
     * </p>
     * 
     * @param q q
     * @return n/q に対する素因数分解
     */
    private PrimeFactorLong dividedByConcrete(long q) {
        long newOriginal = this.original / q;
        SortedMap<Long, Integer> newFactor2Number = new TreeMap<>(this.factor2Number);

        Long qLong = Long.valueOf(q);
        Integer kInteger = newFactor2Number.get(qLong);
        int k = kInteger.intValue();

        if (k == 1) {
            newFactor2Number.remove(qLong);
        } else {
            newFactor2Number.put(qLong, Integer.valueOf(k - 1));
        }

        return new PrimeFactorLong(newOriginal, newFactor2Number);
    }

    /**
     * インスタンスが等価かどうかを判定する. <br>
     * equality の仕様はクラス説明文の通りである.
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
    public final String toString() {
        return "PrimeFactors(long, original = %s, factors = %s)"
                .formatted(this.original(), Arrays.toString(this.factors()));
    }

    /**
     * インスタンスを比較する. <br>
     * comparability の仕様はクラス説明文の通りである.
     */
    @Override
    public final int compareTo(PrimeFactorLong o) {
        return Long.compare(this.original, o.original);
    }

    /**
     * {@code this} から素因数をひとつだけ取り除いた
     * {@link PrimeFactorInt} の重複なしのバリエーションを列挙するイテレータを返す.
     * 
     * <p>
     * 自身の <i>n</i> が1の場合, イテレータは空になる.
     * </p>
     * 
     * @return 素因数をひとつだけ取り除いた素因数分解のイテレータ
     */
    public final Iterator<PrimeFactorLong> subFactorsIterator() {
        return new SubFactorsIterator();
    }

    private final class SubFactorsIterator implements Iterator<PrimeFactorLong> {

        private final long[] qs;

        private int cursor;

        /**
         * 唯一のコンストラクタ.
         * 
         * <p>
         * エンクロージングインスタンスは素数であってはいけない.
         * </p>
         */
        SubFactorsIterator() {
            this.qs = factor2Number.keySet().stream()
                    .mapToLong(i -> i.longValue())
                    .toArray();
            this.cursor = 0;
        }

        @Override
        public boolean hasNext() {
            return cursor < qs.length;
        }

        @Override
        public PrimeFactorLong next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }

            long q = qs[cursor];
            cursor++;

            return PrimeFactorLong.this.dividedByConcrete(q);
        }
    }
}
