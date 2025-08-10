/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.10
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
 * {@link ModulusLong} をテストするためのテスト骨格.
 * 
 * @author Matsuura Y.
 */
@Ignore
final class ModulusLongTesting {

    private ModulusLongTesting() {
        throw new AssertionError();
    }

    @Ignore
    @RunWith(Theories.class)
    static abstract class Prod2 {

        @DataPoints
        public static Fixture[] FIXTURES;

        @DataPoints
        public static long[] divisors = {
                1, 2, 4, 8, 16, 31, 126, 30513, 2874127,
                100000000001L, 1L << 42, 11 * (1L << 42)
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
            long m = fixture.m;
            long x = fixture.x;
            long y = fixture.y;

            try {
                ModulusLong modulus = getModulusLong(m);
                if (modulus.divisor() != m) {
                    throw new AssertionError("assert: getModulusInt: modulus.divisor() != divisor");
                }

                executeTestProduct(modulus, x, y);
            } catch (UnsupportedOperationException igonred) {
                // mが対応していない場合は無視する
            }
        }

        @Theory
        public void test_2値積のモジュロをテスト_ランダム化(long divisor) {

            int ite = 100;

            try {
                ModulusLong modulus = getModulusLong(divisor);
                if (modulus.divisor() != divisor) {
                    throw new AssertionError("assert: getModulusInt: modulus.divisor() != divisor");
                }

                for (int c = 0; c < ite; c++) {

                    long x = ThreadLocalRandom.current().nextLong(divisor * 5);
                    long y = ThreadLocalRandom.current().nextLong(divisor * 5);
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
         * 与えた値を除数とする ModulusLong を返す.
         * 
         * @param m 除数
         * @return
         * @throws UnsupportedOperationException
         *             引数の値が対応しておらず, ModulusLong を返せない場合
         */
        abstract ModulusLong getModulusLong(long m);

        private static void executeTestProduct(ModulusLong modulus, long x, long y) {
            long result = modulus.modpr(x, y);
            long expected = BigInteger.valueOf(x)
                    .multiply(BigInteger.valueOf(y))
                    .mod(BigInteger.valueOf(modulus.divisor()))
                    .longValueExact();

            assertThat(result, is(expected));
        }

        static final class Fixture {
            final long m;
            final long x;
            final long y;

            Fixture(long m, long x, long y) {
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
        public static long[] divisors = {
                1, 2, 4, 8, 16, 31, 126, 30513, 2874127,
                100000000001L, 1L << 42, 11 * (1L << 42)
        };

        @BeforeClass
        public static void before_fixtureの用意() {
            List<Fixture> list = new ArrayList<>();

            list.add(new Fixture(3, new long[] { 4, 2, 7 }));
            list.add(new Fixture(5, new long[] { -6, 2, 4 }));
            list.add(new Fixture(36, new long[] { 881, -64, -31 }));
            list.add(new Fixture(21, new long[] { 3, 382, -216 }));
            list.add(new Fixture(17, new long[] { 64, 25, 541 }));
            list.add(new Fixture(10, new long[] { -52, 83, -52 }));
            list.add(new Fixture(38, new long[] { -543, 1281, 2195 }));
            list.add(new Fixture(100213, new long[] { -543, 1281, 1000351, 318162 }));

            FIXTURES = list.toArray(Fixture[]::new);
        }

        @Theory
        public void test_配列の要素積のモジュロをテスト(Fixture fixture) {
            long m = fixture.m;
            long[] x = fixture.x;

            try {
                ModulusLong modulus = getModulusLong(m);
                if (modulus.divisor() != m) {
                    throw new AssertionError("assert: getModulusInt: modulus.divisor() != divisor");
                }

                executeTestProduct(getModulusLong(m), x);
            } catch (UnsupportedOperationException igonred) {
                // mが対応していない場合は無視する
            }
        }

        @Theory
        public void test_配列の要素積のモジュロをテスト_ランダム化(long divisor) {

            int ite = 100;

            try {
                ModulusLong modulus = getModulusLong(divisor);
                if (modulus.divisor() != divisor) {
                    throw new AssertionError("assert: getModulusInt: modulus.divisor() != divisor");
                }

                for (int c = 0; c < ite; c++) {

                    int size = ThreadLocalRandom.current().nextInt(10);
                    long[] x = new long[size];
                    for (int i = 0; i < size; i++) {
                        x[i] = ThreadLocalRandom.current().nextLong(divisor * 5);
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
         * 与えた値を除数とする ModulusLong を返す.
         * 
         * @param m 除数
         * @return
         * @throws UnsupportedOperationException
         *             引数の値が対応しておらず, ModulusLong を返せない場合
         */
        abstract ModulusLong getModulusLong(long m);

        private static void executeTestProduct(ModulusLong modulus, long[] x) {
            long result = modulus.modpr(x);
            long expected;
            {
                BigInteger m = BigInteger.valueOf(modulus.divisor());
                BigInteger r = BigInteger.ONE;
                for (long xi : x) {
                    r = r.multiply(BigInteger.valueOf(xi))
                            .mod(m);
                }

                expected = r.mod(m).longValueExact();
            }

            assertThat(result, is(expected));
        }

        static final class Fixture {
            final long m;
            final long[] x;

            Fixture(long m, long[] x) {
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
        public static long[] divisors = {
                1, 2, 4, 8, 16, 31, 126, 30513, 2874127,
                100000000001L, 1L << 42, 11 * (1L << 42)
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
            long m = fixture.m;
            long x = fixture.x;
            long k = fixture.k;

            try {
                ModulusLong modulus = getModulusLong(m);
                if (modulus.divisor() != m) {
                    throw new AssertionError("assert: getModulusInt: modulus.divisor() != divisor");
                }

                executeTestPow(getModulusLong(m), x, k);
            } catch (UnsupportedOperationException igonred) {
                // mが対応していない場合は無視する
            }
        }

        @Theory
        public void test_累乗のモジュロをテスト_ランダム化(long divisor) {

            int ite = 100;

            try {
                ModulusLong modulus = getModulusLong(divisor);
                if (modulus.divisor() != divisor) {
                    throw new AssertionError("assert: getModulusInt: modulus.divisor() != divisor");
                }

                for (int c = 0; c < ite; c++) {

                    long x = ThreadLocalRandom.current().nextLong(divisor * 5);
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
         * 与えた値を除数とする ModulusLong を返す.
         * 
         * @param m 除数
         * @return
         * @throws UnsupportedOperationException
         *             引数の値が対応しておらず, ModulusLong を返せない場合
         */
        abstract ModulusLong getModulusLong(long m);

        private static void executeTestPow(ModulusLong modulus, long x, long k) {
            assert k >= 0;

            long result = modulus.modpow(x, k);
            long expected = BigInteger.valueOf(x).modPow(
                    BigInteger.valueOf(k),
                    BigInteger.valueOf(modulus.divisor()))
                    .longValueExact();

            assertThat(result, is(expected));
        }

        static final class Fixture {
            final long m;
            final long x;
            final long k;

            Fixture(long m, long x, long k) {
                super();
                this.m = m;
                this.x = x;
                this.k = k;
            }
        }
    }
}
