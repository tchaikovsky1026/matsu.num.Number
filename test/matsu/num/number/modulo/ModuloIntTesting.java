/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.8
 */
package matsu.num.number.modulo;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * {@link ModuloInt} をテストするためのテスト骨格.
 * 
 * @author Matsuura Y.
 */
@Ignore
final class ModuloIntTesting {

    private ModuloIntTesting() {
        throw new AssertionError();
    }

    @Ignore
    @RunWith(Theories.class)
    static abstract class Prod2 {

        @DataPoints
        public static Fixture[] FIXTURES;

        @DataPoints
        public static int[] divisors = {
                1, 2, 4, 8, 16, 31, 126, 30513, 2874127,
                1000000001, 1 << 20, 11 * (1 << 20)
        };

        @BeforeClass
        public static void before_fixtureの用意() {
            List<Fixture> list = new ArrayList<>();

            list.add(new Fixture(3, 4, 2));
            list.add(new Fixture(5, -6, 2));
            list.add(new Fixture(36, 881, -64));
            list.add(new Fixture(21, 3, 382));
            list.add(new Fixture(17, 64, 25));
            list.add(new Fixture(10, -52, 83));
            list.add(new Fixture(38, -543, 1281));
            list.add(new Fixture(100213, -543, 1281));

            FIXTURES = list.toArray(Fixture[]::new);
        }

        @Theory
        public void test_2値積のモジュロをテスト(Fixture fixture) {
            int m = fixture.m;
            int x = fixture.x;
            int y = fixture.y;

            try {
                ModuloInt modulus = getModulusInt(m);
                if (modulus.divisor() != m) {
                    throw new AssertionError("assert: getModulusInt: modulus.divisor() != divisor");
                }

                executeTestProduct(modulus, x, y);
            } catch (UnsupportedOperationException igonred) {
                // mが対応していない場合は無視する
            }
        }

        @Theory
        public void test_2値積のモジュロをテスト_ランダム化(int divisor) {

            int ite = 100;

            try {
                ModuloInt modulus = getModulusInt(divisor);
                if (modulus.divisor() != divisor) {
                    throw new AssertionError("assert: getModulusInt: modulus.divisor() != divisor");
                }

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
            } catch (UnsupportedOperationException igonred) {
                // divisorが対応していない場合は無視する
            }
        }

        /**
         * 与えた値を除数とする ModulusInt を返す.
         * 
         * @param m 除数
         * @return
         * @throws UnsupportedOperationException
         *             引数の値が対応しておらず, ModulusInt を返せない場合
         */
        abstract ModuloInt getModulusInt(int m);

        private static void executeTestProduct(ModuloInt modulus, int x, int y) {
            int result = modulus.modpr(x, y);
            int expected = BigInteger.valueOf(x)
                    .multiply(BigInteger.valueOf(y))
                    .mod(BigInteger.valueOf(modulus.divisor()))
                    .intValueExact();

            assertThat(result, is(expected));
        }

        static final class Fixture {
            final int m;
            final int x;
            final int y;

            Fixture(int m, int x, int y) {
                super();
                this.m = m;
                this.x = x;
                this.y = y;
            }
        }
    }

    @Ignore
    @RunWith(Theories.class)
    static abstract class ProdArray {

        @DataPoints
        public static Fixture[] FIXTURES;

        @DataPoints
        public static int[] divisors = {
                1, 2, 4, 8, 16, 31, 126, 30513, 2874127,
                1000000001, 1 << 20, 11 * (1 << 20)
        };

        @BeforeClass
        public static void before_fixtureの用意() {
            List<Fixture> list = new ArrayList<>();

            list.add(new Fixture(3, new int[] { 4, 2, 7 }));
            list.add(new Fixture(5, new int[] { -6, 2, 4 }));
            list.add(new Fixture(36, new int[] { 881, -64, -31 }));
            list.add(new Fixture(21, new int[] { 3, 382, -216 }));
            list.add(new Fixture(17, new int[] { 64, 25, 541 }));
            list.add(new Fixture(10, new int[] { -52, 83, -52 }));
            list.add(new Fixture(38, new int[] { -543, 1281, 2195 }));
            list.add(new Fixture(100213, new int[] { -543, 1281, 1000351, 318162 }));

            FIXTURES = list.toArray(Fixture[]::new);
        }

        @Theory
        public void test_配列の要素積のモジュロをテスト(Fixture fixture) {
            int m = fixture.m;
            int[] x = fixture.x;

            try {
                ModuloInt modulus = getModulusInt(m);
                if (modulus.divisor() != m) {
                    throw new AssertionError("assert: getModulusInt: modulus.divisor() != divisor");
                }

                executeTestProduct(getModulusInt(m), x);
            } catch (UnsupportedOperationException igonred) {
                // mが対応していない場合は無視する
            }
        }

        @Theory
        public void test_配列の要素積のモジュロをテスト_ランダム化(int divisor) {

            int ite = 100;

            try {
                ModuloInt modulus = getModulusInt(divisor);
                if (modulus.divisor() != divisor) {
                    throw new AssertionError("assert: getModulusInt: modulus.divisor() != divisor");
                }

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
            } catch (UnsupportedOperationException igonred) {
                // divisorが対応していない場合は無視する
            }
        }

        /**
         * 与えた値を除数とする ModulusInt を返す.
         * 
         * @param m 除数
         * @return
         * @throws UnsupportedOperationException
         *             引数の値が対応しておらず, ModulusInt を返せない場合
         */
        abstract ModuloInt getModulusInt(int m);

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

        static final class Fixture {
            final int m;
            final int[] x;

            Fixture(int m, int[] x) {
                super();
                this.m = m;
                this.x = x;
            }
        }
    }

    @Ignore
    @RunWith(Theories.class)
    static abstract class Pow {

        @DataPoints
        public static Fixture[] FIXTURES;

        @DataPoints
        public static int[] divisors = {
                1, 2, 4, 8, 16, 31, 126, 30513, 2874127,
                1000000001, 1 << 20, 11 * (1 << 20)
        };

        @BeforeClass
        public static void before_fixtureの用意() {
            List<Fixture> list = new ArrayList<>();

            list.add(new Fixture(3, 4, 2));
            list.add(new Fixture(5, -6, 2));
            list.add(new Fixture(36, 881, 64));
            list.add(new Fixture(21, 3, 0));
            list.add(new Fixture(17, 64, 25));
            list.add(new Fixture(10, -52, 83));
            list.add(new Fixture(38, -543, 1281));
            list.add(new Fixture(100213, -543, 1281));

            FIXTURES = list.toArray(Fixture[]::new);
        }

        @Theory
        public void test_累乗のモジュロをテスト(Fixture fixture) {
            int m = fixture.m;
            int x = fixture.x;
            int k = fixture.k;

            try {
                ModuloInt modulus = getModulusInt(m);
                if (modulus.divisor() != m) {
                    throw new AssertionError("assert: getModulusInt: modulus.divisor() != divisor");
                }

                executeTestPow(getModulusInt(m), x, k);
            } catch (UnsupportedOperationException igonred) {
                // mが対応していない場合は無視する
            }
        }

        @Theory
        public void test_累乗のモジュロをテスト_ランダム化(int divisor) {

            int ite = 100;

            try {
                ModuloInt modulus = getModulusInt(divisor);
                if (modulus.divisor() != divisor) {
                    throw new AssertionError("assert: getModulusInt: modulus.divisor() != divisor");
                }

                for (int c = 0; c < ite; c++) {

                    int x = ThreadLocalRandom.current().nextInt(divisor * 5);
                    if (ThreadLocalRandom.current().nextBoolean()) {
                        x = -x;
                    }
                    int k = ThreadLocalRandom.current().nextInt(200);

                    executeTestPow(modulus, x, k);
                }
            } catch (UnsupportedOperationException igonred) {
                // divisorが対応していない場合は無視する
            }
        }

        /**
         * 与えた値を除数とする ModulusInt を返す.
         * 
         * @param m 除数
         * @return
         * @throws UnsupportedOperationException
         *             引数の値が対応しておらず, ModulusInt を返せない場合
         */
        abstract ModuloInt getModulusInt(int m);

        private static void executeTestPow(ModuloInt modulus, int x, int k) {
            assert k >= 0;

            int result = modulus.modpow(x, k);
            int expected = BigInteger.valueOf(x).modPow(
                    BigInteger.valueOf(k),
                    BigInteger.valueOf(modulus.divisor()))
                    .intValueExact();

            assertThat(result, is(expected));
        }

        static final class Fixture {
            final int m;
            final int x;
            final int k;

            Fixture(int m, int x, int k) {
                super();
                this.m = m;
                this.x = x;
                this.k = k;
            }
        }
    }
}
