/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.primes;

import java.util.function.LongFunction;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import matsu.num.number.speedutil.SpeedTestExecutor;

/**
 * {@link PollardBrentRhoLong} のテスト.
 */
@RunWith(Enclosed.class)
final class PollardBrentRhoLongTest {

    public static final Class<?> TEST_CLASS = PollardBrentRhoLong.class;

    private static final PrimeFactorizeLong FACTORIZE_LONG = new PollardBrentRhoLong();

    public static class LongFactorize extends PrimeFactorizeTesting.LongFactorize {

        @Override
        LongFunction<PrimeFactorLong> getPrimeFactorize() {
            return FACTORIZE_LONG::apply;
        }

        @Override
        long max() {
            return 3_000_000_000_000_000L;
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

        private static final long MIN = 1_000_000_000_000L;
        private static final long MAX = 1_000_000_010_000L;

        private long d = 0L;
        public long sum = 0L;

        @Test
        public void test_PollardBrentRhoLongの実行() {
            PrimeFactorizeLong primeFactorizeLong = new PollardBrentRhoLong();
            {
                var executor = new SpeedTestExecutor(
                        TEST_CLASS, "PollardBrentRhoLong: ", 10,
                        () -> {
                            this.d = 0L;
                            long d = this.d;
                            for (long n = MIN; n < MAX; n++) {
                                long[] f = primeFactorizeLong.apply(Math.max(n, d)).factors();
                                d = f[0];
                            }
                            this.d = d;
                            this.sum += d;
                        });
                executor.execute();
            }
        }

        @Test
        public void test_NaiveTrialPrimeFactorizeLongの実行() {
            @SuppressWarnings("deprecation")
            PrimeFactorizeLong primeFactorizeLong = new NaiveTrialPrimeFactorizeLong();
            {
                var executor = new SpeedTestExecutor(
                        TEST_CLASS, "NaiveTrialPrimeFactorizeLong: ", 1,
                        () -> {
                            this.d = 0L;
                            long d = this.d;
                            for (long n = MIN; n < MAX; n++) {
                                long[] f = primeFactorizeLong.apply(Math.max(n, d)).factors();
                                d = f[0];
                            }
                            this.d = d;
                            this.sum += d;
                        });
                executor.execute();
            }
        }
    }

    public static class PrimeFactorLongのtoString表示 {

        @Test
        public void test_toString表示() {
            System.out.println(TEST_CLASS.getName() + ":");
            System.out.println(FACTORIZE_LONG.apply(360L << 32));
            System.out.println();
        }
    }
}
