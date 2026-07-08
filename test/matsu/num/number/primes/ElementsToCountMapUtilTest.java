/*
 * Copyright © 2026 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

package matsu.num.number.primes;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.BeforeClass;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * {@link ElementsToCountMapUtil} のテスト.
 */
@RunWith(Enclosed.class)
final class ElementsToCountMapUtilTest {

    @RunWith(Theories.class)
    public static class Integerテスト {

        @DataPoints
        public static List<Fixture<Integer>> fixtures;

        @BeforeClass
        public static void before_prepareFixtures() {
            fixtures = new ArrayList<>();

            fixtures.add(
                    new Fixture<>(
                            List.of(2, 3, 2),
                            new TreeMap<>(Map.of(3, 1, 2, 2))));
            fixtures.add(
                    new Fixture<>(
                            List.of(2, 3, 2, 3, 3),
                            new TreeMap<>(Map.of(3, 3, 2, 2))));
            fixtures.add(
                    new Fixture<>(
                            List.of(2, 4, 2, 3, 3),
                            new TreeMap<>(Map.of(3, 2, 2, 2, 4, 1))));
        }

        @Theory
        public void test_countMapへの変換テスト(Fixture<Integer> fixture) {
            assertThat(ElementsToCountMapUtil.toCountMap(fixture.src), is(fixture.countingMap));
        }
    }

    @RunWith(Theories.class)
    public static class Longテスト {

        @DataPoints
        public static List<Fixture<Long>> fixtures;

        @BeforeClass
        public static void before_prepareFixtures() {
            fixtures = new ArrayList<>();

            fixtures.add(
                    new Fixture<>(
                            List.of(2L, 3L, 2L),
                            new TreeMap<>(Map.of(3L, 1, 2L, 2))));
            fixtures.add(
                    new Fixture<>(
                            List.of(2L, 3L, 2L, 3L, 3L),
                            new TreeMap<>(Map.of(3L, 3, 2L, 2))));
            fixtures.add(
                    new Fixture<>(
                            List.of(2L, 4L, 2L, 3L, 3L),
                            new TreeMap<>(Map.of(3L, 2, 2L, 2, 4L, 1))));
        }

        @Theory
        public void test_countMapへの変換テスト(Fixture<Integer> fixture) {
            assertThat(ElementsToCountMapUtil.toCountMap(fixture.src), is(fixture.countingMap));
        }
    }

    private static final class Fixture<T extends Comparable<? super T>> {

        final List<T> src;
        final SortedMap<T, Integer> countingMap;

        Fixture(List<T> src, SortedMap<T, Integer> countingMap) {
            super();
            this.src = src;
            this.countingMap = countingMap;
        }
    }
}
