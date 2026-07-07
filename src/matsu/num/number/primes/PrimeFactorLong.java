/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.7.7
 */
package matsu.num.number.primes;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.PrimitiveIterator;
import java.util.SortedMap;
import java.util.Spliterator;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.LongFunction;
import java.util.function.LongUnaryOperator;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * 1 以上の {@code long} 型整数 <i>n</i> の素因数を表すクラス. <br>
 * <i>n</i> の値に基づく equality と comparability を提供する.
 * 
 * <p>
 * このクラスは, イミュータブルかつスレッドセーフであることが保証されている. <br>
 * このクラスは素因数分解の結果を返すための型であり, ユーザーがインスタンスを生成する手段は提供しない.
 * </p>
 * 
 * @author Matsuura Y.
 * @see PrimeFactorize
 */
public final class PrimeFactorLong implements Comparable<PrimeFactorLong> {
    /**
     * 素因数分解前の元の値.
     */
    private final long original;

    /**
     * 素因数分解
     * n = p_1^{k_1} * p_2^{k_2} ...
     * について,
     * {@literal (p_i -> k_i)}
     * を表現する.
     */
    private final SortedMap<Long, Integer> factorToNumber;

    /**
     * original が素数であるかどうか
     */
    private final boolean prime;

    /**
     * 遅延初期化ロック用オブジェクト.
     */
    private final Object lock = new Object();

    /**
     * 素因数分解
     * n = p_1^{k_1} * p_2^{k_2} ...
     * について, [p_1, p_1, ...]
     * と展開したもの.
     * 遅延初期化される.
     */
    private volatile long[] factors;

    /**
     * {@link #subFactorsCollection()}
     * の戻り値.
     * 遅延初期化される.
     */
    private volatile Collection<PrimeFactorLong> subFactorsCollection;

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
        this(original, ElementsToCountMapUtil.toCountMap(factorsList));
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
     * @param factorToNumber 素因数とその個数のマップ:
     *            自然順序のcompare,
     *            Value は1以上,
     *            マップへの参照は外部に漏れていない
     */
    private PrimeFactorLong(long original, SortedMap<Long, Integer> factorToNumber) {
        super();

        this.original = original;
        this.factorToNumber = factorToNumber;
        this.prime = this.factorToNumber.containsKey(Long.valueOf(original));
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
     * <i>n</i> の素因数を配列として返す. <br>
     * 例えば, {@code this.original() == 12} の場合, {@code {2, 3}} が返る.
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
        long[] out = factors;
        if (Objects.nonNull(out)) {
            return out.clone();
        }

        synchronized (lock) {
            out = factors;
            if (Objects.nonNull(out)) {
                return out.clone();
            }

            // SortedMapがキー(素因数)の自然順であるので, array も昇順である
            out = factorToNumber.entrySet().stream()
                    .flatMapToLong(
                            // Entry(素因数, 繰り返し) を Stream(素因数,素因数,...)に変換
                            e -> LongStream
                                    .iterate(
                                            e.getKey().longValue(), LongUnaryOperator.identity())
                                    .limit(e.getValue().intValue()))
                    .toArray();
            factors = out.clone();
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
        return this.factorToNumber.containsKey(Long.valueOf(q))
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
        SortedMap<Long, Integer> newFactor2Number = new TreeMap<>(this.factorToNumber);

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
     * {@code this} から素因数をひとつだけ取り除いた ({@link #dividedBy(long)} を適用した)
     * {@link PrimeFactorLong} の重複なしのバリエーションを列挙するイミュータブルコレクションを返す. <br>
     * 例としては, 次である.
     * 
     * <ul>
     * <li>original = 12 &rarr;
     * {@code {PrimeFactors(6), PrimeFactors(4)}}</li>
     * <li>original = 2 &rarr;
     * {@code {PrimeFactors(1)}}</li>
     * <li>original = 1 &rarr;
     * {@code {}}</li>
     * </ul>
     * 
     * <p>
     * 返される {@link Collection} は,
     * 要素を取得するために毎回 {@link #dividedBy(long)} を実行する可能性がある. <br>
     * そのため, 拡張 {@code for} 文や {@link Stream}
     * を使うことを補助する目的に適している. <br>
     * コレクションを実体として取り扱う場合,
     * 他の {@link Collection} の実装に詰め直したほうが良い.
     * </p>
     * 
     * <p>
     * 自身の <i>n</i> が1の場合, コレクションは空になる.
     * </p>
     * 
     * @return 素因数をひとつだけ取り除いた素因数分解のコレクション
     * @see #dividedBy(long)
     */
    public final Collection<PrimeFactorLong> subFactorsCollection() {

        Collection<PrimeFactorLong> out = this.subFactorsCollection;
        if (Objects.nonNull(out)) {
            return out;
        }

        synchronized (lock) {
            out = this.subFactorsCollection;
            if (Objects.nonNull(out)) {
                return out;
            }

            return this.subFactorsCollection = new SubFactorsCollection();
        }
    }

    /**
     * {@link #subFactorsCollection()} の戻り値となる, サブコレクション.
     * 
     * <p>
     * コレクション要素としての PrimeFactor は, インスタンス生成時には生成されない. <br>
     * イテレータ, スプリッテレータ, ストリームなどで要素にアクセスされた際に,
     * {@link #dividedByConcrete(long)} が実行されて生成される. <br>
     * そのため, 何度も要素にアクセスされる場合は, 実体を要素に持つコレクションに詰め直されるほうが良い.
     * </p>
     */
    private final class SubFactorsCollection extends AbstractCollection<PrimeFactorLong> {

        private final LongFunction<PrimeFactorLong> mapper =
                q -> PrimeFactorLong.this.dividedByConcrete(q);

        private final long[] qs = factorToNumber.keySet().stream()
                .mapToLong(i -> i.longValue())
                .toArray();

        /**
         * 唯一のコストラクタ.
         */
        SubFactorsCollection() {
            super();
        }

        @Override
        public int size() {
            return qs.length;
        }

        @Override
        public Iterator<PrimeFactorLong> iterator() {
            PrimitiveIterator.OfLong qsIte = LongStream.of(qs).iterator();

            return new Iterator<>() {

                @Override
                public boolean hasNext() {
                    return qsIte.hasNext();
                }

                @Override
                public PrimeFactorLong next() {
                    // ここで例外をスローする可能性がある
                    long q = qsIte.nextLong();

                    return mapper.apply(q);
                }
            };
        }

        @Override
        public Spliterator<PrimeFactorLong> spliterator() {
            return new LongToObjMappedSpliterator<PrimeFactorLong>(
                    qs, mapper);
        }

        /*
         * stream(), parallelStream() は Collection のデフォルトの実装が最適.
         */
    }

    /**
     * 不変の整数配列から mapper により要素を生成するスプリッテレータ.
     */
    private static final class LongToObjMappedSpliterator<R> implements Spliterator<R> {

        private final Spliterator.OfLong source;
        private final LongFunction<R> mapper;

        /**
         * エンクロージングクラスから呼ばれる.
         * source は不変を保証しなければならない.
         */
        LongToObjMappedSpliterator(long[] source, LongFunction<R> mapper) {
            this(Arrays.spliterator(source), mapper);
        }

        /**
         * 内部から呼ばれる.
         * エンクロージングから呼んではいけない. <br>
         * source は
         * SIZED,
         * SUBSIZED,
         * ORDERED,
         * IMMUTABLE
         * を報告すること.
         * 
         * @param source
         * @param mapper
         */
        private LongToObjMappedSpliterator(Spliterator.OfLong source, LongFunction<R> mapper) {
            super();
            this.source = source;
            this.mapper = mapper;
        }

        @Override
        public boolean tryAdvance(Consumer<? super R> action) {
            return source.tryAdvance((long v) -> action.accept(mapper.apply(v)));
        }

        @Override
        public void forEachRemaining(Consumer<? super R> action) {
            source.forEachRemaining((long v) -> action.accept(mapper.apply(v)));
        }

        @Override
        public Spliterator<R> trySplit() {
            Spliterator.OfLong splitSource = source.trySplit();
            return splitSource == null
                    ? null
                    : new LongToObjMappedSpliterator<>(splitSource, mapper);
        }

        @Override
        public long estimateSize() {
            return source.estimateSize();
        }

        @Override
        public int characteristics() {
            return source.characteristics() | Spliterator.NONNULL;
        }
    }
}
