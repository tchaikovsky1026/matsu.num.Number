/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

package matsu.num.number.primes;

import java.util.function.IntFunction;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import matsu.num.number.speedutil.SpeedTestExecutor;

/**
 * {@link PollardBrentRhoInt} のテスト.
 */
@RunWith(Enclosed.class)
final class PollardBrentRhoIntTest {

    public static final Class<?> TEST_CLASS = PollardBrentRhoInt.class;

    private static final PrimeFactorizeInt FACTORIZE_INT = new PollardBrentRhoInt();

    public static class IntFactorize extends PrimeFactorizeTesting.IntFactorize {

        @Override
        IntFunction<int[]> getPrimeFactorize() {
            return n -> FACTORIZE_INT.apply(n).factors();
        }

        @Override
        int max() {
            return 1_000_000_000;
        }
    }

    /**
     * 素因数分解の計算時間評価.
     * 
     * <p>
     * このテストは, 被除数を一様にとってテストしているので,
     * MIN_RHOの値を評価するには不十分である. <br>
     * 参考情報として目安にするにとどめる.
     * </p>
     */
    @Ignore
    public static class 計算時間評価 {

        private static final int MIN = 2_000_000_000;
        private static final int MAX = 2_000_500_000;

        private int d = 0;
        public int sum = 0;

        @Test
        public void test_PollardBrentRhoIntの実行() {
            PrimeFactorizeInt primeFactorizeInt = new PollardBrentRhoInt();
            {
                var executor = new SpeedTestExecutor(
                        TEST_CLASS, "PollardBrentRhoInt: ", 1,
                        () -> {
                            this.d = 0;
                            int d = this.d;
                            for (int n = MIN; n < MAX; n++) {
                                int[] f = primeFactorizeInt.apply(Math.max(n, d)).factors();
                                d = f[0];
                            }
                            this.d = d;
                            this.sum += d;
                        });
                executor.execute();
            }
        }

        @Test
        public void test_NaiveTrialPrimeFactorizeIntの実行() {
            @SuppressWarnings("deprecation")
            PrimeFactorizeInt primeFactorizeInt = new NaiveTrialPrimeFactorizeInt();
            {
                var executor = new SpeedTestExecutor(
                        TEST_CLASS, "NaiveTrialPrimeFactorizeInt: ", 1,
                        () -> {
                            this.d = 0;
                            int d = this.d;
                            for (int n = MIN; n < MAX; n++) {
                                int[] f = primeFactorizeInt.apply(Math.max(n, d)).factors();
                                d = f[0];
                            }
                            this.d = d;
                            this.sum += d;
                        });
                executor.execute();
            }
        }
    }

    public static class PrimeFactorIntのtoString表示 {

        @Test
        public void test_toString表示() {
            System.out.println(TEST_CLASS.getName() + ":");
            System.out.println(FACTORIZE_INT.apply(360));
            System.out.println();
        }
    }
}
