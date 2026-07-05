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
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntFunction;

import org.junit.Ignore;
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

        @Theory
        public final void test_2値積のモジュロをテスト_ランダム化(int divisor) {

            int ite = 1000;
            ModuloInt modulus = getModulo().apply(divisor);
            for (int c = 0; c < ite; c++) {
                int x = ThreadLocalRandom.current().nextInt();
                int y = ThreadLocalRandom.current().nextInt();
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
    }

    /**
     * {@link ModuloInt#modpr(int, int)} のテスト. <br>
     * 継承先で, 除数を表す {@code @DataPoints int[]} の整備が必要.
     */
    @Ignore
    @RunWith(Theories.class)
    static abstract class ProdArray {

        @Theory
        public final void test_配列の要素積のモジュロをテスト_ランダム化(int divisor) {

            int ite = 1000;
            ModuloInt modulus = getModulo().apply(divisor);
            for (int c = 0; c < ite; c++) {

                int size = ThreadLocalRandom.current().nextInt(10);
                int[] x = new int[size];
                for (int i = 0; i < size; i++) {
                    x[i] = ThreadLocalRandom.current().nextInt();
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
    }

    /**
     * {@link ModuloInt#modpr(int, int)} のテスト. <br>
     * 継承先で, 除数を表す {@code @DataPoints int[]} の整備が必要.
     */
    @Ignore
    @RunWith(Theories.class)
    static abstract class Pow {

        @Theory
        public final void test_累乗のモジュロをテスト_ランダム化(int divisor) {

            int ite = 1000;
            ModuloInt modulus = getModulo().apply(divisor);
            for (int c = 0; c < ite; c++) {
                int x = ThreadLocalRandom.current().nextInt();
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
            int ite = 1000;
            ModuloInt modulus = getModulo().apply(divisor);
            for (int c = 0; c < ite; c++) {
                int a = ThreadLocalRandom.current().nextInt();
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
