/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.7.31
 */
package matsu.num.number.modulo;

/**
 * 単純なモジュロ演算の実装.
 * 
 * @author Matsuura Y.
 * @deprecated このクラスはプロダクトコードからは使用されていない
 */
@Deprecated
abstract class SimpleModulusInt extends SkeletalModulusInt {

    private final int m;

    /**
     * 引数は1以上でなければならない.
     */
    private SimpleModulusInt(int m) {
        super();
        assert m >= 1;
        this.m = m;
    }

    @Override
    public final int divisor() {
        return this.m;
    }

    @Override
    public final int mod(int x) {
        int out = x % m;
        return out >= 0
                ? out
                : out + m;
    }

    @Override
    public abstract int modpr(int x, int y);

    @Override
    public final int modpr(int... x) {
        int out = 1;
        for (int xi : x) {
            out = modpr(out, mod(xi));
        }
        return mod(out);
    }

    @Override
    public final int modpow(int x, int k) {
        if (k < 0) {
            throw new IllegalArgumentException("illegal: exponent k is negative: k = " + k);
        }
        int out = 1;
        int xPow2 = mod(x);
        while (k > 0) {
            if ((k & 1) == 1) {
                out = modpr(out, xPow2);
            }
            k >>>= 1;
            xPow2 = modpr(xPow2, xPow2);
        }
        return mod(out);
    }

    /**
     * {@code int} 型整数について,
     * 与えた正の整数を除数とするモジュロ演算を返す.
     * 
     * <p>
     * 引数の値は正でなければならない.
     * </p>
     * 
     * @param divisor 除数
     * @return 除数に対応するモジュロ演算
     * @throws IllegalArgumentException 引数が正の整数でない場合
     */
    static SimpleModulusInt of(int divisor) {
        if (divisor <= 0) {
            throw new IllegalArgumentException("illegal: divisor <= 0");
        }

        return divisor <= SmallDivisorImpl.MAX_M
                ? new SmallDivisorImpl(divisor)
                : new LargeDivisorImpl(divisor);
    }

    /**
     * 除数が小さく, 積でオーバーフローの危険のない場合の実装.
     */
    private static final class SmallDivisorImpl extends SimpleModulusInt {

        private static final int MAX_M = 46340;

        SmallDivisorImpl(int m) {
            super(m);
            assert m <= MAX_M;
        }

        @Override
        public int modpr(int x, int y) {

            /*
             * m <= MAX_<(46340) ならば,
             * 46340^2 = 2_147_395_600 < 2^31 - 1
             * でオーバーフローしない.
             */
            return mod(mod(x) * mod(y));
        }
    }

    /**
     * 除数が大きく, 積でオーバーフローのリスクがある場合の実装.
     */
    private static final class LargeDivisorImpl extends SimpleModulusInt {

        LargeDivisorImpl(int m) {
            super(m);
        }

        @Override
        public int modpr(int x, int y) {
            // x, yを正規化: 0以上となる.
            x = mod(x);
            y = mod(y);

            /*
             * x*y <= 2^31 - 1 を保証したい.
             * Integer.numberOfLeadingZeros を nlz と書く.
             * 
             * 任意の0以上の値 v について,
             * v <= 2^(32 - nlz(v)) - 1 であるので,
             * x*y <= 2^(64 - nlz(x) - nlz(y)) - 1 である.
             * よって,
             * nlz(x) + nlz(y) >= 33 ならよい.
             */
            int numLeadZero_x = Integer.numberOfLeadingZeros(x);
            int numLeadZero_y = Integer.numberOfLeadingZeros(y);
            if (numLeadZero_x + numLeadZero_y >= 33) {
                return mod(x * y);
            }

            // 単純なオーバーフロー回避
            return (int) (((long) x * y) % this.divisor());
        }
    }
}
