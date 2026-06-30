/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.modint;

import java.util.function.IntFunction;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.runner.RunWith;

import matsu.num.number.ModuloInt;
import matsu.num.number.speedutil.SpeedTestExecutor;

/** {@link ModuloMontgomery} クラスのテスト. */
@RunWith(Enclosed.class)
final class ModuloMontgomeryTest {

    public static final Class<?> TEST_CLASS = ModuloMontgomery.class;

    private static final int[] DIVISORS = {
            3, 5, 7, 9, 11, 13, 15, 17, 19,
            31, 30513, 2874127, 1000000001
    };

    public static class ModProd2のテスト extends ModuloTestingUtil.Prod2 {

        @DataPoints
        public static int[] divisors = DIVISORS;

        @Override
        IntFunction<ModuloInt> getModulo() {
            return ModuloMontgomery::new;
        }
    }

    public static class ModProdArrayのテスト extends ModuloTestingUtil.ProdArray {

        @DataPoints
        public static int[] divisors = DIVISORS;

        @Override
        IntFunction<ModuloInt> getModulo() {
            return ModuloMontgomery::new;
        }
    }

    public static class ModPowのテスト extends ModuloTestingUtil.Pow {

        @DataPoints
        public static int[] divisors = DIVISORS;

        @Override
        IntFunction<ModuloInt> getModulo() {
            return ModuloMontgomery::new;
        }
    }

    public static class GcdInverseのテスト extends ModuloTestingUtil.GcdInverse {

        @DataPoints
        public static int[] divisors = DIVISORS;

        @Override
        IntFunction<ModuloInt> getModulo() {
            return ModuloMontgomery::new;
        }
    }

    @Ignore
    public static class 計算時間評価 {

        private int m = 1001;
        private int k = 100;

        private int d = 100000;

        @Test
        public void test_MontgomeryIntの実行() {
            ModuloInt modulusInt = new ModuloMontgomery(m);
            {
                var executor = new SpeedTestExecutor(
                        TEST_CLASS, "MontgomeryInt:mod: ", 10_000_000,
                        () -> {
                            int d = this.d;
                            int mask = 0x7FFF_FFFF;
                            d += modulusInt.mod(d);
                            d &= mask;
                            d += modulusInt.mod(d);
                            d &= mask;
                            d += modulusInt.mod(d);
                            d &= mask;
                            d += modulusInt.mod(d);
                            d &= mask;
                            d += modulusInt.mod(d);
                            d &= mask;
                            d += modulusInt.mod(d);
                            d &= mask;
                            d += modulusInt.mod(d);
                            d &= mask;
                            d += modulusInt.mod(d);
                            d &= mask;
                            d += modulusInt.mod(d);
                            d &= mask;
                            d += modulusInt.mod(d);
                            d &= mask;
                            this.d = d;
                        });
                executor.execute();
            }
            {
                var executor = new SpeedTestExecutor(
                        TEST_CLASS, "MontgomeryInt:modpr2: ", 10_000_000,
                        () -> {
                            int d = this.d;
                            int mask = 0x7FFF_FFFF;
                            d += modulusInt.modpr(d, d);
                            d &= mask;
                            d += modulusInt.modpr(d, d);
                            d &= mask;
                            d += modulusInt.modpr(d, d);
                            d &= mask;
                            d += modulusInt.modpr(d, d);
                            d &= mask;
                            d += modulusInt.modpr(d, d);
                            d &= mask;
                            d += modulusInt.modpr(d, d);
                            d &= mask;
                            d += modulusInt.modpr(d, d);
                            d &= mask;
                            d += modulusInt.modpr(d, d);
                            d &= mask;
                            d += modulusInt.modpr(d, d);
                            d &= mask;
                            d += modulusInt.modpr(d, d);
                            d &= mask;
                            this.d = d;
                        });
                executor.execute();
            }
            {
                var executor = new SpeedTestExecutor(
                        TEST_CLASS, "MontgomeryInt:modpow: ", 2_000_000,
                        () -> {
                            int d = this.d;
                            int k = this.k;
                            int mask = 0x7FFF_FFFF;
                            d += modulusInt.modpow(d, k);
                            d &= mask;
                            d += modulusInt.modpow(d, k);
                            d &= mask;
                            d += modulusInt.modpow(d, k);
                            d &= mask;
                            d += modulusInt.modpow(d, k);
                            d &= mask;
                            d += modulusInt.modpow(d, k);
                            d &= mask;
                            d += modulusInt.modpow(d, k);
                            d &= mask;
                            d += modulusInt.modpow(d, k);
                            d &= mask;
                            d += modulusInt.modpow(d, k);
                            d &= mask;
                            d += modulusInt.modpow(d, k);
                            d &= mask;
                            d += modulusInt.modpow(d, k);
                            d &= mask;
                            this.d = d;
                        });
                executor.execute();
            }
        }

        @Test
        public void test_SimpleModulusIntの実行() {
            @SuppressWarnings("deprecation")
            ModuloInt modulusInt = SimpleModuloInt.of(m);
            {
                var executor = new SpeedTestExecutor(
                        TEST_CLASS, "SimpleModulusInt:mod: ", 10_000_000,
                        () -> {
                            int d = this.d;
                            int mask = 0x7FFF_FFFF;
                            d += modulusInt.mod(d);
                            d &= mask;
                            d += modulusInt.mod(d);
                            d &= mask;
                            d += modulusInt.mod(d);
                            d &= mask;
                            d += modulusInt.mod(d);
                            d &= mask;
                            d += modulusInt.mod(d);
                            d &= mask;
                            d += modulusInt.mod(d);
                            d &= mask;
                            d += modulusInt.mod(d);
                            d &= mask;
                            d += modulusInt.mod(d);
                            d &= mask;
                            d += modulusInt.mod(d);
                            d &= mask;
                            d += modulusInt.mod(d);
                            d &= mask;
                            this.d = d;
                        });
                executor.execute();
            }
            {
                var executor = new SpeedTestExecutor(
                        TEST_CLASS, "SimpleModulusInt:modpr2: ", 10_000_000,
                        () -> {
                            int d = this.d;
                            int mask = 0x7FFF_FFFF;
                            d += modulusInt.modpr(d, d);
                            d &= mask;
                            d += modulusInt.modpr(d, d);
                            d &= mask;
                            d += modulusInt.modpr(d, d);
                            d &= mask;
                            d += modulusInt.modpr(d, d);
                            d &= mask;
                            d += modulusInt.modpr(d, d);
                            d &= mask;
                            d += modulusInt.modpr(d, d);
                            d &= mask;
                            d += modulusInt.modpr(d, d);
                            d &= mask;
                            d += modulusInt.modpr(d, d);
                            d &= mask;
                            d += modulusInt.modpr(d, d);
                            d &= mask;
                            d += modulusInt.modpr(d, d);
                            d &= mask;
                            this.d = d;
                        });
                executor.execute();
            }
            {
                var executor = new SpeedTestExecutor(
                        TEST_CLASS, "SimpleModulusInt:modpow: ", 2_000_000,
                        () -> {
                            int d = this.d;
                            int k = this.k;
                            int mask = 0x7FFF_FFFF;
                            d += modulusInt.modpow(d, k);
                            d &= mask;
                            d += modulusInt.modpow(d, k);
                            d &= mask;
                            d += modulusInt.modpow(d, k);
                            d &= mask;
                            d += modulusInt.modpow(d, k);
                            d &= mask;
                            d += modulusInt.modpow(d, k);
                            d &= mask;
                            d += modulusInt.modpow(d, k);
                            d &= mask;
                            d += modulusInt.modpow(d, k);
                            d &= mask;
                            d += modulusInt.modpow(d, k);
                            d &= mask;
                            d += modulusInt.modpow(d, k);
                            d &= mask;
                            d += modulusInt.modpow(d, k);
                            d &= mask;
                            this.d = d;
                        });
                executor.execute();
            }
        }
    }
}
