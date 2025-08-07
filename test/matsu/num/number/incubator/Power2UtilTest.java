/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

package matsu.num.number.incubator;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.BeforeClass;
import org.junit.experimental.runners.Enclosed;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * {@link Power2Util} のテスト.
 */
@RunWith(Enclosed.class)
final class Power2UtilTest {

    @RunWith(Theories.class)
    public static class int型のExponentOf2のテスト {

        @DataPoints
        public static Fixture[] fixtures;

        @BeforeClass
        public static void before_テストデータの用意() {
            fixtures = new Fixture[] {
                    new Fixture(1, 0),
                    new Fixture(2, 1),
                    new Fixture(4, 2),
                    new Fixture(256, 8),
                    new Fixture(1 << 30, 30),
                    new Fixture(3, -1),
                    new Fixture(5, -1),
                    new Fixture(Integer.MAX_VALUE, -1)
            };
        }

        @Theory
        public void test_exponent(Fixture fixture) {
            assertThat(Power2Util.exponentOf2(fixture.n), is(fixture.exponent));
        }

        static class Fixture {
            final int n;
            final int exponent;

            Fixture(int n, int exponent) {
                super();
                this.n = n;
                this.exponent = exponent;
            }

        }
    }

    @RunWith(Theories.class)
    public static class long型のExponentOf2のテスト {

        @DataPoints
        public static Fixture[] fixtures;

        @BeforeClass
        public static void before_テストデータの用意() {
            fixtures = new Fixture[] {
                    new Fixture(1L, 0),
                    new Fixture(2L, 1),
                    new Fixture(4L, 2),
                    new Fixture(256L, 8),
                    new Fixture(1L << 62, 62),
                    new Fixture(3L, -1),
                    new Fixture(5L, -1),
                    new Fixture(Long.MAX_VALUE, -1)
            };
        }

        @Theory
        public void test_exponent(Fixture fixture) {
            assertThat(Power2Util.exponentOf2(fixture.n), is(fixture.exponent));
        }

        static class Fixture {
            final long n;
            final int exponent;

            Fixture(long n, int exponent) {
                super();
                this.n = n;
                this.exponent = exponent;
            }

        }
    }
}
