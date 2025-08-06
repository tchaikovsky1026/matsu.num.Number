/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

package matsu.num.number.incubator.modulo;

import java.util.function.IntFunction;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import matsu.num.number.speedutil.SpeedTestExecutor;

/**
 * {@link MontgomeryInt} クラスのテスト.
 */
@RunWith(Enclosed.class)
final class MontgomeryIntTest {

    public static final Class<?> TEST_CLASS = MontgomeryInt.class;

    private static final IntFunction<ModulusInt> moduloGetter =
            m -> {
                if ((m % 2) == 0) {
                    throw new UnsupportedOperationException();
                }
                return MontgomeryInt.of(m);
            };

    public static class ModProd2のテスト extends ModulusIntTesting.Prod2 {

        @Override
        ModulusInt getModulusInt(int m) {
            return moduloGetter.apply(m);
        }
    }

    public static class ModProdArrayのテスト extends ModulusIntTesting.ProdArray {

        @Override
        ModulusInt getModulusInt(int m) {
            return moduloGetter.apply(m);
        }
    }

    public static class ModPowのテスト extends ModulusIntTesting.Pow {

        @Override
        ModulusInt getModulusInt(int m) {
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
            ModulusInt modulusInt = MontgomeryInt.of(m);
            {
                var executor = new SpeedTestExecutor(
                        TEST_CLASS, "MontgomeryInt:mod: ", 10_000_000,
                        () -> {
                            d += modulusInt.mod(d * d);
                            d += modulusInt.mod(d * d);
                            d += modulusInt.mod(d * d);
                            d += modulusInt.mod(d * d);
                            d += modulusInt.mod(d * d);
                            d += modulusInt.mod(d * d);
                            d += modulusInt.mod(d * d);
                            d += modulusInt.mod(d * d);
                            d += modulusInt.mod(d * d);
                            d += modulusInt.mod(d * d);
                        });
                executor.execute();
            }
            {
                var executor = new SpeedTestExecutor(
                        TEST_CLASS, "MontgomeryInt:modpr2: ", 10_000_000,
                        () -> {
                            d += modulusInt.modpr(d, d);
                            d += modulusInt.modpr(d, d);
                            d += modulusInt.modpr(d, d);
                            d += modulusInt.modpr(d, d);
                            d += modulusInt.modpr(d, d);
                            d += modulusInt.modpr(d, d);
                            d += modulusInt.modpr(d, d);
                            d += modulusInt.modpr(d, d);
                            d += modulusInt.modpr(d, d);
                            d += modulusInt.modpr(d, d);
                        });
                executor.execute();
            }
            {
                var executor = new SpeedTestExecutor(
                        TEST_CLASS, "MontgomeryInt:modpow: ", 2_000_000,
                        () -> {
                            d += modulusInt.modpow(d, k);
                            d += modulusInt.modpow(d, k);
                            d += modulusInt.modpow(d, k);
                            d += modulusInt.modpow(d, k);
                            d += modulusInt.modpow(d, k);
                            d += modulusInt.modpow(d, k);
                            d += modulusInt.modpow(d, k);
                            d += modulusInt.modpow(d, k);
                            d += modulusInt.modpow(d, k);
                            d += modulusInt.modpow(d, k);
                        });
                executor.execute();
            }
        }

        @Test
        public void test_SimpleModulusIntの実行() {
            ModulusInt modulusInt = SimpleModulusInt.of(m);
            {
                var executor = new SpeedTestExecutor(
                        TEST_CLASS, "SimpleModulusInt:mod: ", 10_000_000,
                        () -> {
                            d += modulusInt.mod(d * d);
                            d += modulusInt.mod(d * d);
                            d += modulusInt.mod(d * d);
                            d += modulusInt.mod(d * d);
                            d += modulusInt.mod(d * d);
                            d += modulusInt.mod(d * d);
                            d += modulusInt.mod(d * d);
                            d += modulusInt.mod(d * d);
                            d += modulusInt.mod(d * d);
                            d += modulusInt.mod(d * d);
                        });
                executor.execute();
            }
            {
                var executor = new SpeedTestExecutor(
                        TEST_CLASS, "SimpleModulusInt:modpr2: ", 10_000_000,
                        () -> {
                            d += modulusInt.modpr(d, d);
                            d += modulusInt.modpr(d, d);
                            d += modulusInt.modpr(d, d);
                            d += modulusInt.modpr(d, d);
                            d += modulusInt.modpr(d, d);
                            d += modulusInt.modpr(d, d);
                            d += modulusInt.modpr(d, d);
                            d += modulusInt.modpr(d, d);
                            d += modulusInt.modpr(d, d);
                            d += modulusInt.modpr(d, d);
                        });
                executor.execute();
            }
            {
                var executor = new SpeedTestExecutor(
                        TEST_CLASS, "SimpleModulusInt:modpow: ", 2_000_000,
                        () -> {
                            d += modulusInt.modpow(d, k);
                            d += modulusInt.modpow(d, k);
                            d += modulusInt.modpow(d, k);
                            d += modulusInt.modpow(d, k);
                            d += modulusInt.modpow(d, k);
                            d += modulusInt.modpow(d, k);
                            d += modulusInt.modpow(d, k);
                            d += modulusInt.modpow(d, k);
                            d += modulusInt.modpow(d, k);
                            d += modulusInt.modpow(d, k);
                        });
                executor.execute();
            }
        }
    }
}
