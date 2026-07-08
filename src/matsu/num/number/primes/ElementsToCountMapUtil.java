/*
 * Copyright © 2026 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.7.7
 */
package matsu.num.number.primes;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * {@link Comparable} な要素を持つコレクションを,
 * 要素とその個数の {@link SortedMap} に変換するためのユーティリティ.
 * 
 * <p>
 * [2,2,3] と表現されたコレクションを,
 * {@literal SortedMap[2 -> 2, 3 -> 1]} (要素から個数へのマップ) と表現する.
 * </p>
 * 
 * @author Matsuura Y.
 */
final class ElementsToCountMapUtil {

    private ElementsToCountMapUtil() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /**
     * 要素コレクションを, 個数マップに変換する. <br>
     * 個数マップ (SortedMap) は, キーの自然順序で並ぶ.
     * 
     * @param elements 要素のコレクション
     * @return 要素から個数へのマップ
     * @throws NullPointerException 引数にnullを含む場合
     */
    static <T extends Comparable<? super T>> SortedMap<T, Integer> toCountMap(
            Collection<? extends T> elements) {

        CollectorHolder<T> holder = CollectorHolder.instance();

        return elements.stream()
                .collect(holder.get());
    }

    /**
     * {@code Collector<T, ?, SortedMap<T, Integer>>　toCountMapCollector}
     * をジェネリックシングルトンで扱うためのホルダ.
     * 
     * <p>
     * 2箇所に {@code T} が出現するので,
     * 単一のパラメータとなるように {@code CollectorHolder<T>} でラップした. <br>
     * {@code CollectorHolder<T>} からは {@link #get()} メソッドで
     * {@code Collector<T, ?, SortedMap<T, Integer>>} が取得できる. <br>
     * さらに, {@code CollectorHolder<T>} の実体はシングルトンにできるので,
     * {@code CollectorHolder<Comparable<...>>} をキャストして返すジェネリックメソッド
     * {@link #instance()} を用意した.
     * </p>
     */
    private static final class CollectorHolder<T extends Comparable<? super T>> {

        private final Collector<T, ?, SortedMap<T, Integer>> collector;

        /** 非公開コンストラクタ, 内部から呼ばれる. */
        private CollectorHolder() {
            Collector<Object, ?, Integer> countingCollector = Collectors.collectingAndThen(
                    Collectors.counting(),
                    (Long count) -> Integer.valueOf(count.intValue()));

            this.collector = Collectors.groupingBy(
                    o -> o, TreeMap<T, Integer>::new, countingCollector);
        }

        /**
         * このクラスのインスタンスを返す.
         * 
         * @param <T> T
         * @return {@code CollectorHolder<T>}
         */
        static <T extends Comparable<? super T>> CollectorHolder<T> instance() {

            // このキャストは問題ない
            @SuppressWarnings("unchecked")
            CollectorHolder<T> out = (CollectorHolder<T>) SingletonHolder.INSTANCE;
            return out;
        }

        /**
         * Collector を取得する. <br>
         * 型パラメータ {@code T} を生かすための仕組み.
         * 
         * @return Collector
         */
        Collector<T, ?, SortedMap<T, Integer>> get() {
            return collector;
        }

        /** シングルトンホルダ. 初期化を遅らせるための仕組み. */
        private static final class SingletonHolder {

            /**
             * {@link CollectorHolder} クラスのシングルトンインスタンス. <br>
             * インスタンスの取得には, ジェネリックメソッド {@link #instance()} を使う.
             */
            static final CollectorHolder<
                    Comparable<? super Comparable<?>>> INSTANCE = new CollectorHolder<>();
        }
    }
}
