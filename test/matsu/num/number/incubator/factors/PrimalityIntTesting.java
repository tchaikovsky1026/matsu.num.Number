/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.9
 */
package matsu.num.number.incubator.factors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * {@link PrimalityInt} の実装をテストするためのテスト骨格.
 * 
 * @author Matsuura Y.
 */
@Ignore
final class PrimalityIntTesting {

    private PrimalityIntTesting() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /**
     * 小さい整数に関して, 素数を列挙して検証する.
     */
    @Ignore
    @RunWith(Theories.class)
    static abstract class Enumeration {

        private static final int test_min;
        private static final int test_max;
        private static final Set<Integer> primes;

        @DataPoints
        public static final int[] values;

        static {

            /*
             * -5 から 1000 までの素数を扱う.
             */
            int[] primesArr = {
                    2, 3, 5, 7, 11, 13, 17, 19, 23, 29,
                    31, 37, 41, 43, 47, 53, 59, 61, 67, 71,
                    73, 79, 83, 89, 97, 101, 103, 107, 109, 113,
                    127, 131, 137, 139, 149, 151, 157, 163, 167, 173,
                    179, 181, 191, 193, 197, 199, 211, 223, 227, 229,
                    233, 239, 241, 251, 257, 263, 269, 271, 277, 281,
                    283, 293, 307, 311, 313, 317, 331, 337, 347, 349,
                    353, 359, 367, 373, 379, 383, 389, 397, 401, 409,
                    419, 421, 431, 433, 439, 443, 449, 457, 461, 463,
                    467, 479, 487, 491, 499, 503, 509, 521, 523, 541,
                    547, 557, 563, 569, 571, 577, 587, 593, 599, 601,
                    607, 613, 617, 619, 631, 641, 643, 647, 653, 659,
                    661, 673, 677, 683, 691, 701, 709, 719, 727, 733,
                    739, 743, 751, 757, 761, 769, 773, 787, 797, 809,
                    811, 821, 823, 827, 829, 839, 853, 857, 859, 863,
                    877, 881, 883, 887, 907, 911, 919, 929, 937, 941,
                    947, 953, 967, 971, 977, 983, 991, 997
            };
            test_min = -5;
            test_max = 1000;

            values = IntStream.rangeClosed(test_min, test_max).toArray();

            primes = Arrays.stream(primesArr)
                    .mapToObj(p -> Integer.valueOf(p))
                    .collect(
                            Collectors.collectingAndThen(
                                    Collectors.toList(),
                                    HashSet::new));

        }

        @Theory
        public void test_素数であるかを検証する(int v) {
            boolean expected = primes.contains(v);
            boolean result = getPrimalityInt().isPrime(v);

            String displayString = createDisplayString(v, result, expected);
            assertThat(displayString, result, is(expected));
        }

        /**
         * 検証に使う素数判定器を返す.
         * 
         * @return 素数判定器
         */
        abstract PrimalityInt getPrimalityInt();
    }

    /**
     * ランダムな整数に対して素数判定を検証する.
     */
    @Ignore
    static abstract class Randoms {

        private static final PrimalityInt REFERENCE = new NaivePrimalityInt();

        @Test
        public void test_素数検証_ランダム化() {
            int ite = 1000;

            PrimalityInt testingPrimalityInt = getPrimalityInt();

            for (int c = 0; c < ite; c++) {

                int n = ThreadLocalRandom.current().nextInt(1_000_000);

                boolean result = testingPrimalityInt.isPrime(n);
                boolean expected = REFERENCE.isPrime(n);

                String displayString = createDisplayString(n, result, expected);
                assertThat(displayString, result, is(expected));
            }
        }

        /**
         * 検証に使う素数判定器を返す.
         * 
         * @return 素数判定器
         */
        abstract PrimalityInt getPrimalityInt();
    }

    private static String createDisplayString(int v, boolean result, boolean expected) {
        return v + " is " + (expected ? "prime" : "not prime") +
                ", result = \"" + (result ? "prime" : "not prime") +
                "\"";
    }
}
