/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.7.6
 */
package matsu.num.number.primes.modint;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.function.IntFunction;

import org.junit.Ignore;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import matsu.num.number.primes.PrimeModuloInt;

/**
 * int の PrimeModulo に関して検証する.
 * 
 * @author Matsuura Y.
 */
@Ignore
@RunWith(Theories.class)
abstract class PrimeModuloIntTesting {

    @DataPoints
    public static int[] ps =
            { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29 };

    @Theory
    public void test_位数の検証(int p) {
        PrimeModuloInt primeModulo = getPrimeModulo().apply(p);

        // a = 1 から a = p-1 まで位数を検証する
        for (int a = 1; a < p; a++) {

            //素朴な繰り返しにより位数を計算
            int expected = 0;
            int r = 1;
            do {
                r = primeModulo.modpr(r, a);
                expected++;
            } while (r != 1);

            assertThat(
                    "p = %s, a = %s".formatted(p, a),
                    primeModulo.order(a), is(expected));
        }
    }

    @Theory
    public void test_逆元の検証(int p) {
        PrimeModuloInt primeModulo = getPrimeModulo().apply(p);

        // a = 1 から a = p-1 まで逆元を検証する
        for (int a = 1; a < p; a++) {

            int invA = primeModulo.inverse(a);

            assertThat(
                    "check a*invA mod p == 1. p = %s, a = %s".formatted(p, a),
                    primeModulo.modpr(a, invA), is(1));
        }
    }

    @Theory
    public void test_原始根判定の検証(int p) {
        PrimeModuloInt primeModulo = getPrimeModulo().apply(p);

        // a = 1 から a = p-1 まで位数を比較する
        for (int a = 1; a < p; a++) {
            assertThat(
                    "p = %s, a = %s".formatted(p, a),
                    primeModulo.isPrimitiveRoot(a),
                    is(primeModulo.order(a) == p - 1));
        }
    }

    @Theory
    public void test_原始根の生成の検証(int p) {
        PrimeModuloInt primeModulo = getPrimeModulo().apply(p);

        int a = primeModulo.primitiveRoot();
        assertThat(
                "p = %s".formatted(p),
                primeModulo.isPrimitiveRoot(a), is(true));
    }

    /**
     * 検証に使う PrimeModulo の生成器を返す.
     * 
     * @return PrimeModulo の生成器
     */
    abstract IntFunction<PrimeModuloInt> getPrimeModulo();
}
