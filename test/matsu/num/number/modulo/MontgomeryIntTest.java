/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.modulo;

import java.util.function.IntFunction;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import matsu.num.number.ModuloInt;
import matsu.num.number.speedutil.SpeedTestExecutor;

/**
 * {@link MontgomeryInt} クラスのテスト.
 */
@RunWith(Enclosed.class)
final class MontgomeryIntTest {

    public static final Class<?> TEST_CLASS = MontgomeryInt.class;

    private static final IntFunction<ModuloInt> moduloGetter =
            m -> {
                if (m == 1) {
                    throw new UnsupportedOperationException();
                }
                if ((m % 2) == 0) {
                    throw new UnsupportedOperationException();
                }
                return new MontgomeryInt(m);
            };

    public static class ModProd2のテスト extends ModuloIntTesting.Prod2 {

        @Override
        ModuloInt getModulusInt(int m) {
            return moduloGetter.apply(m);
        }
    }

    public static class ModProdArrayのテスト extends ModuloIntTesting.ProdArray {

        @Override
        ModuloInt getModulusInt(int m) {
            return moduloGetter.apply(m);
        }
    }

    public static class ModPowのテスト extends ModuloIntTesting.Pow {

        @Override
        ModuloInt getModulusInt(int m) {
            return moduloGetter.apply(m);
        }
    }

    @Ignore
    public static class 計算時間評価 {

        private int m = 1001;
        private int k = 100;

        private int d = 100000;

        @Test
        public void test_MontgomeryIntの実行() {
            ModuloInt modulusInt = new MontgomeryInt(m);
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
