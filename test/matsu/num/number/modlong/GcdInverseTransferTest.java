/*
 * Copyright © 2026 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.modlong;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import matsu.num.number.Gcd;
import matsu.num.number.ModuloLong;

/**
 * {@link GcdInverseTransfer} のテスト.
 * 
 * @author Matsuura Y.
 */
@RunWith(Enclosed.class)
final class GcdInverseTransferTest {

    @RunWith(Theories.class)
    public static class gcd逆数のパラメータテスト {

        @DataPoints
        public static final long[] divisors = {
                1, 2, 3, 4, 5, 60, 100, 32768, Integer.MAX_VALUE - 1, Integer.MAX_VALUE,
                Long.MAX_VALUE - 1, Long.MAX_VALUE
        };

        @Theory
        public void test_ランダムaテスト(long divisor) {
            ModuloLong modulo = ModuloLong.get(divisor);

            int iteration = 1000;
            for (int c = 0; c < iteration; c++) {
                long a = ThreadLocalRandom.current().nextLong();
                a = modulo.mod(a);

                long calcR = GcdInverseTransfer.gcdInverse(a, modulo);

                long rightGcd = modulo.mod(Gcd.gcd(a, divisor));
                long leftMult = modulo.modpr(a, calcR);

                assertThat("range of gcdInverse", calcR, is(modulo.mod(calcR)));
                assertThat(
                        "a = %s, calcR = %s, a*calcR = %s".formatted(a, calcR, leftMult),
                        leftMult, is(rightGcd));
            }
        }
    }
}
