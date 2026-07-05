/*
 * Copyright © 2026 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.6.30
 */
package matsu.num.number.modint;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntFunction;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import matsu.num.number.Gcd;
import matsu.num.number.ModuloInt;

/**
 * {@link ModuloInt} をテストするためのテスト骨格. <br>
 * 
 * <p>
 * ネストしたクラスを継承し, 適切に実装することで,
 * {@link ModuloInt} のテストクラスができる. <br>
 * 継承することとは別に, DataPointsの実装が必要になる場合がある. <br>
 * 詳細はネストクラスを参照.
 * <p>
 * 
 * <p>
 * このクラス自体はインスタンス化不可.
 * <p>
 * 
 * @author Matsuura Y.
 */
@Ignore
final class ModuloTestingUtil {

    private ModuloTestingUtil() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /**
     * {@link ModuloInt#modpr(int, int)} のテスト. <br>
     * 継承先で, 除数を表す {@code @DataPoints int[]} の整備が必要.
     */
    @Ignore
    @RunWith(Theories.class)
    static abstract class Prod2 {

        @DataPoints
        public static Fixture[] FIXTURES;

        @BeforeClass
        public static void before_fixtureの用意() {
            List<Fixture> list = new ArrayList<>();

            list.add(new Fixture(4, 2));
            list.add(new Fixture(-6, 2));
            list.add(new Fixture(881, -64));
            list.add(new Fixture(3, 382));
            list.add(new Fixture(64, 25));
            list.add(new Fixture(-52, 83));
            list.add(new Fixture(-543, 1281));

            FIXTURES = list.toArray(Fixture[]::new);
        }

        @Theory
        public final void test_2値積のモジュロをテスト(Fixture fixture, int divisor) {
            int x = fixture.x;
            int y = fixture.y;
            ModuloInt modulus = getModulo().apply(divisor);
            executeTestProduct(modulus, x, y);
        }

        @Theory
        public final void test_2値積のモジュロをテスト_ランダム化(int divisor) {

            int ite = 100;
            ModuloInt modulus = getModulo().apply(divisor);
            for (int c = 0; c < ite; c++) {

                int x = ThreadLocalRandom.current().nextInt(divisor * 5);
                int y = ThreadLocalRandom.current().nextInt(divisor * 5);
                if (ThreadLocalRandom.current().nextBoolean()) {
                    x = -x;
                }
                if (ThreadLocalRandom.current().nextBoolean()) {
                    y = -y;
                }

                executeTestProduct(modulus, x, y);
            }
        }

        /**
         * divisor を与えて ModulusInt を返す関数.
         */
        abstract IntFunction<ModuloInt> getModulo();

        private static void executeTestProduct(ModuloInt modulus, int x, int y) {
            int result = modulus.modpr(x, y);
            int expected = BigInteger.valueOf(x)
                    .multiply(BigInteger.valueOf(y))
                    .mod(BigInteger.valueOf(modulus.divisor()))
                    .intValueExact();

            assertThat(result, is(expected));
        }

        private static final class Fixture {
            final int x;
            final int y;

            Fixture(int x, int y) {
                super();
                this.x = x;
                this.y = y;
            }
        }
    }

    /**
     * {@link ModuloInt#modpr(int, int)} のテスト. <br>
     * 継承先で, 除数を表す {@code @DataPoints int[]} の整備が必要.
     */
    @Ignore
    @RunWith(Theories.class)
    static abstract class ProdArray {

        @DataPoints
        public static Fixture[] FIXTURES;

        @BeforeClass
        public static void before_fixtureの用意() {
            List<Fixture> list = new ArrayList<>();

            list.add(new Fixture(new int[] { 4, 2, 7 }));
            list.add(new Fixture(new int[] { -6, 2, 4 }));
            list.add(new Fixture(new int[] { 881, -64, -31 }));
            list.add(new Fixture(new int[] { 3, 382, -216 }));
            list.add(new Fixture(new int[] { 64, 25, 541 }));
            list.add(new Fixture(new int[] { -52, 83, -52 }));
            list.add(new Fixture(new int[] { -543, 1281, 2195 }));
            list.add(new Fixture(new int[] { -543, 1281, 1000351, 318162 }));

            FIXTURES = list.toArray(Fixture[]::new);
        }

        @Theory
        public final void test_配列の要素積のモジュロをテスト(Fixture fixture, int divisor) {
            int[] x = fixture.x;
            ModuloInt modulus = getModulo().apply(divisor);
            executeTestProduct(modulus, x);
        }

        @Theory
        public final void test_配列の要素積のモジュロをテスト_ランダム化(int divisor) {

            int ite = 100;

            ModuloInt modulus = getModulo().apply(divisor);

            for (int c = 0; c < ite; c++) {

                int size = ThreadLocalRandom.current().nextInt(10);
                int[] x = new int[size];
                for (int i = 0; i < size; i++) {
                    x[i] = ThreadLocalRandom.current().nextInt(divisor * 5);
                    if (ThreadLocalRandom.current().nextBoolean()) {
                        x[i] = -x[i];
                    }
                }

                executeTestProduct(modulus, x);
            }
        }

        /**
         * divisor を与えて ModulusInt を返す関数.
         */
        abstract IntFunction<ModuloInt> getModulo();

        private static void executeTestProduct(ModuloInt modulus, int[] x) {
            int result = modulus.modpr(x);
            int expected;
            {
                BigInteger m = BigInteger.valueOf(modulus.divisor());
                BigInteger r = BigInteger.ONE;
                for (int xi : x) {
                    r = r.multiply(BigInteger.valueOf(xi))
                            .mod(m);
                }

                expected = r.mod(m).intValueExact();
            }

            assertThat(result, is(expected));
        }

        private static final class Fixture {
            final int[] x;

            Fixture(int[] x) {
                super();
                this.x = x;
            }
        }
    }

    /**
     * {@link ModuloInt#modpr(int, int)} のテスト. <br>
     * 継承先で, 除数を表す {@code @DataPoints int[]} の整備が必要.
     */
    @Ignore
    @RunWith(Theories.class)
    static abstract class Pow {

        @DataPoints
        public static Fixture[] FIXTURES;

        @BeforeClass
        public static void before_fixtureの用意() {
            List<Fixture> list = new ArrayList<>();

            list.add(new Fixture(4, 2));
            list.add(new Fixture(-6, 2));
            list.add(new Fixture(881, 64));
            list.add(new Fixture(3, 0));
            list.add(new Fixture(64, 25));
            list.add(new Fixture(-52, 83));
            list.add(new Fixture(-543, 1281));
            list.add(new Fixture(-543, 1281));

            FIXTURES = list.toArray(Fixture[]::new);
        }

        @Theory
        public final void test_累乗のモジュロをテスト(Fixture fixture, int divisor) {
            int x = fixture.x;
            int k = fixture.k;

            ModuloInt modulus = getModulo().apply(divisor);
            executeTestPow(modulus, x, k);
        }

        @Theory
        public final void test_累乗のモジュロをテスト_ランダム化(int divisor) {

            int ite = 100;

            ModuloInt modulus = getModulo().apply(divisor);

            for (int c = 0; c < ite; c++) {

                int x = ThreadLocalRandom.current().nextInt(divisor * 5);
                if (ThreadLocalRandom.current().nextBoolean()) {
                    x = -x;
                }
                int k = ThreadLocalRandom.current().nextInt(200);

                executeTestPow(modulus, x, k);
            }
        }

        /**
         * divisor を与えて ModulusInt を返す関数.
         */
        abstract IntFunction<ModuloInt> getModulo();

        private static void executeTestPow(ModuloInt modulus, int x, int k) {
            assert k >= 0;

            int result = modulus.modpow(x, k);
            int expected = BigInteger.valueOf(x).modPow(
                    BigInteger.valueOf(k),
                    BigInteger.valueOf(modulus.divisor()))
                    .intValueExact();

            assertThat(result, is(expected));
        }

        private static final class Fixture {
            final int x;
            final int k;

            Fixture(int x, int k) {
                super();
                this.x = x;
                this.k = k;
            }
        }
    }

    /**
     * {@link ModuloInt#modpr(int, int)} のテスト. <br>
     * 継承先で, 除数を表す {@code @DataPoints int[]} の整備が必要.
     */
    @Ignore
    @RunWith(Theories.class)
    static abstract class GcdInverse {

        @Theory
        public final void test_GcdInverseをテスト_ランダム化(int divisor) {
            int ite = 100;
            ModuloInt modulus = getModulo().apply(divisor);
            for (int c = 0; c < ite; c++) {
                int a = ThreadLocalRandom.current().nextInt(divisor * 5);
                int r = modulus.gcdInverse(a);
                int gcd = Gcd.gcd(a, divisor);
                assertThat(
                        "a = " + a + "m = " + divisor + "gcd(a,m) = " + gcd,
                        modulus.modpr(a, r), is(modulus.mod(gcd)));
            }
        }

        /**
         * divisor を与えて ModulusInt を返す関数.
         */
        abstract IntFunction<ModuloInt> getModulo();
    }
}
