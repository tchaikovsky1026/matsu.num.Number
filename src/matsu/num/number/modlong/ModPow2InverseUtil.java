/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.7.5
 */
package matsu.num.number.modlong;

/**
 * 64-bit 整数の 2^64 に対するモジュロ逆元を計算するユーティリティ. <br>
 * 符号有り/無しを問わない.
 * 
 * <p>
 * 機能メモ: <br>
 * m に対し, ym = 1 mod 2^64 とする. <br>
 * このとき, 1 以上 64 以下の d について, ym = 1 mod 2^d である. <br>
 * すなわちこの機能は, 2^d を法とした逆元の計算に使用できる.
 * </p>
 * 
 * @author Matsuura Y.
 */
final class ModPow2InverseUtil {

    private ModPow2InverseUtil() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /**
     * {@code long} 型整数について, R = 2^64 を法とした逆元を計算する.
     * 
     * <p>
     * n は奇数でなければならない. <br>
     * ただし, このバリデーションは行われない.
     * </p>
     * 
     * @param n n
     * @return R = 2^64 を法とした n の逆元
     */
    static long invModR(long n) {
        assert (n & 1L) == 1L;

        /*
         * (n は奇数であることに注意.)
         * r = 1 - y*n を表し, r = 0 となるように y を決定する.
         * 初期値は y = 0 (よって r = 1) とする.
         * 
         * y は下位 bit から順番に決定していく.
         * y の下位 a-bit を決定しようとしているとき (a >= 0), 上記 r は (a-1)-bit 以下はすべて 0
         * で埋まっている.
         * そこで, y の下位 a-bit を決定しようとしているときは,
         * r としては (a-1)-bit 以下があふれるように r >>> a を考えればよい.
         */

        long y = 0;
        // r >>> a の値を表す.
        long currentR = 1L;
        // r >>> a の真の値を表現するためのマスク, 処理が進むと上位bitが 0 で埋まる
        long currentRMask = -1L;
        // 現在処理している y　の桁の bit が立っている
        long currentBitSetMask = 1L;
        while (currentR != 0) {
            if ((currentR & 1L) == 1L) {
                // y の注目ビットを立てる
                y |= currentBitSetMask;

                currentR -= n;
                // この時点では, currentR の上位 bit にゴミが入っている.
            }

            //currentR の上位 bit のゴミを消去することで, 真の (r >>> a) の値に変換
            currentR &= currentRMask;

            // y の注目 bit を1つ上げる
            currentR >>>= 1;
            currentRMask >>>= 1;
            currentBitSetMask <<= 1;
        }

        return y;
    }
}
