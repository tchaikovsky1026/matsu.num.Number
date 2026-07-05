/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.modlong;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * {@link ModPow2InverseUtil} のテスト.
 */
@RunWith(Enclosed.class)
final class ModPow2InverseUtilTest {

    public static final Class<?> TEST_CLASS = ModPow2InverseUtil.class;

    public static class invModRのテスト {

        @Test
        public void test_invModR() {
            final int iteration = 10000;

            for (int c = 0; c < iteration; c++) {
                long n = ThreadLocalRandom.current().nextInt() | 1L;
                assertThat(
                        "calculated n*inv(n) at n = %s is:".formatted(n),
                        ModPow2InverseUtil.invModR(n) * n, is(1L));
            }
        }
    }
}
