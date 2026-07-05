/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2026.6.26
 */
package matsu.num.number;

/**
 * 整数型の乗算に関連する機能を扱う.
 * 
 * @author Matsuura Y.
 */
public final class MultUtil {

    private MultUtil() {
        // インスタンス化不可
        throw new AssertionError();
    }

    /*
     * ==========================================
     * int 型に対する乗算ユーティリティ.
     * ==========================================
     * 
     * int 型に対する積演算は, long 型を経由するのが高速である.
     * high-32-bit を得る場合であっても, 64-bit long で計算するので十分である.
     */

    /**
     * 32bit整数 <i>x</i>, <i>y</i> について,
     * <i>x</i><i>y</i> を64bitで計算した結果を返す.
     * 
     * @param x <i>x</i>
     * @param y <i>y</i>
     * @return <i>x</i><i>y</i> の64bit
     */
    public static long multiplyFull(int x, int y) {
        return (long) x * y;
    }

    /**
     * 32bit整数 <i>x</i>, <i>y</i> について,
     * <i>x</i><i>y</i> を64bitで計算したときの上位32bitを計算する.
     * 
     * <p>
     * 下位32bitを計算するメソッド {@code multiplyLow} は提供していない. <br>
     * 下位32bitは {@code int} の通常の積を {@code int} 型で受け取ることで得られる.
     * </p>
     * 
     * @param x <i>x</i>
     * @param y <i>y</i>
     * @return <i>x</i><i>y</i> の上位32bit
     */
    public static int multiplyHigh(int x, int y) {
        return (int) (multiplyFull(x, y) >> 32);
    }

    /**
     * 32bit整数 <i>x</i>, <i>y</i> を符号無しと見なし,
     * <i>x</i><i>y</i> を64bitで計算した結果を返す.
     * 
     * @param x <i>x</i> (符号無しと見なす)
     * @param y <i>y</i> (符号無しと見なす)
     * @return <i>x</i><i>y</i> の64bit
     */
    public static long unsignedMultiplyFull(int x, int y) {
        // long 型に変換したうえで, 上位32-bitを0埋め
        // -> unsigned で解釈したものとして long 型になる
        return (x & 0xFFFF_FFFFL) * (y & 0xFFFF_FFFFL);
    }

    /**
     * 32bit整数 <i>x</i>, <i>y</i> を符号無しと見なし,
     * <i>x</i><i>y</i> を64bitで計算したときの上位32bitを計算する.
     * 
     * <p>
     * 下位32bitを計算するメソッド {@code unsignedMultiplyLow} は提供していない. <br>
     * 下位32bitは {@code int} の通常の積を {@code int} 型で受け取ることで得られる.
     * </p>
     * 
     * @param x <i>x</i> (符号無しと見なす)
     * @param y <i>y</i> (符号無しと見なす)
     * @return <i>x</i><i>y</i> の上位32bit
     */
    public static int unsignedMultiplyHigh(int x, int y) {
        // ビットシフトは符号有り, 符号なしのどちらでも int へのキャストで同一の結果を返す.
        // 意味的な解釈で, 符号なしシフトで実装した.
        return (int) (unsignedMultiplyFull(x, y) >>> 32);
    }

    /*
     * ==========================================
     * long 型に対する乗算ユーティリティ.
     * ==========================================
     * 
     * long 型に対する積演算は, 上位, 下位に分割して処理する必要がある.
     * 符号あり, 符号なしの各アルゴリズムについて, コメントとして整備する.
     */

    /*
     * ------------------------------------------
     * 符号有り long 乗算アルゴリズム
     * ------------------------------------------
     * 
     * x,y を符号あり 64-bit と見なし,
     * x = x1 * (2^32) + x2,
     * y = y1 * (2^32) + y2
     * となる x1, x2, y1, y2 を計算する.
     * ただし, x1, y1 は符号あり 32-bit (-2^31 以上 2^31 - 1 以下),
     * x2, y2 は符号なし 32-bit (0 以上 2^32 - 1 以下) である.
     * これは, (x >> 32) や ( x & 0xFFFF_FFFFL) で実現.
     * x*y = (x1*y1)*(2^64) + (x1*y2 + x2*y1)*(2^32) + (x2*y2)
     * である.
     * ここで, (x2*y2) だけは, 符号なし64bitと見なす.
     * 
     * 符号あり 32-bit と符号なし 32-bit の積は -(2^63) 以上, 2^63 - 1 - 2^32 以下であるので,
     * x1*y2, x2*y1 に2^32 以下の値を加えてもオーバーフローを起こさないという性質をこの後に使う.
     * 
     * x2*y2 = r1 * (2^32) + r2 とし (上位 32-bit と下位 32-bit に分け),
     * t = x1*y2 + r1 とすれば,
     * x*y = (x1*y1)*(2^64) + (t + x2*y1)*(2^32) + r2
     * となり, さらに t = t1 * (2^32) + t2 とすれば,
     * x*y = (x1*y1 + t1)*(2^64) + (t2 + x2*y1)*(2^32) + r2
     * となる.
     * s = t2 + x2*y1 = s1 * (2^32) + s2 とすれば,
     * x*y = (x1*y1 + t1 + s1)*(2^64) + s2 * (2^32) + r2
     * となる.
     * 
     * 確認として, r1 = r >>> 32 であり, t1 = t >> 32, s1 = s >> 32 である.
     * (符号の有無に注意)
     */

    /**
     * 内部から呼ばれる. <br>
     * 64bit整数 <i>x</i>, <i>y</i> について,
     * <i>x</i><i>y</i> を符号付き128bitで計算した結果を計算し, 引数の配列に格納する.
     * 
     * @param x x
     * @param y y
     * @param result 結果格納用, {上位, 下位}, サイズ2でなければならない
     */
    private static void multiplyFullLongHelper(long x, long y, long[] result) {

        /* 符号有り long 乗算アルゴリズムに従う. */

        long x1 = x >> 32;
        long x2 = x & 0xFFFF_FFFFL;
        long y1 = y >> 32;
        long y2 = y & 0xFFFF_FFFFL;

        long r = x2 * y2;
        long r1 = r >>> 32;

        long t = x1 * y2 + r1;
        long t1 = t >> 32;
        long t2 = t & 0xFFFF_FFFFL;

        long s = t2 + x2 * y1;

        result[0] = x1 * y1 + t1 + (s >> 32);
        result[1] = (s << 32) | (r & 0xFFFF_FFFFL);
    }

    /**
     * 64bit整数 <i>x</i>, <i>y</i> について,
     * <i>x</i><i>y</i> を128bitで計算した結果を計算して, 配列として返す.
     * 
     * <p>
     * 結果は {@code long} の配列として返される. <br>
     * 配列はサイズ2であり, 第0要素に上位bit, 第1要素に下位bitが格納されている.
     * </p>
     * 
     * @param x <i>x</i>
     * @param y <i>y</i>
     * @return <i>x</i><i>y</i> の128bit, {上位64bit, 下位64bit}
     */
    public static long[] multiplyFullLong(long x, long y) {
        long[] out = new long[2];
        multiplyFullLongHelper(x, y, out);
        return out;
    }

    /**
     * 64bit整数 <i>x</i>, <i>y</i> について,
     * <i>x</i><i>y</i> を128bitで計算した結果を計算し, 引数の配列に格納する.
     * 
     * <p>
     * 結果は引数の {@code long} の配列に格納される. <br>
     * 第0要素に上位bit, 第1要素に下位bitが格納されている. <br>
     * 配列はサイズ2でなければならない.
     * </p>
     * 
     * @param x <i>x</i>
     * @param y <i>y</i>
     * @param result <i>x</i><i>y</i> の128bitを格納するための配列,
     *            {上位64bit, 下位64bit} が格納される.
     * @throws IllegalArgumentException 引数の配列がサイズ2でない場合
     * @throws NullPointerException 引数にnullが含まれる場合
     */
    public static void multiplyFullLong(long x, long y, long[] result) {
        if (result.length != 2) {
            throw new IllegalArgumentException(
                    "illegal: result.length != 2: length = " + result.length);
        }
        multiplyFullLongHelper(x, y, result);
    }

    /**
     * 64bit整数 <i>x</i>, <i>y</i> について,
     * <i>x</i><i>y</i> を128bitで計算したときの上位64bitを計算する.
     * 
     * <p>
     * 下位64bitを計算するメソッド {@code multiplyLowLong} は提供していない. <br>
     * 下位64bitは {@code long} の通常の積を {@code long} 型で受け取ることで得られる.
     * </p>
     * 
     * @param x <i>x</i>
     * @param y <i>y</i>
     * @return <i>x</i><i>y</i> の上位64bit
     */
    public static long multiplyHighLong(long x, long y) {

        /* 符号有り long 乗算アルゴリズムに従う. */

        long x1 = x >> 32;
        long x2 = x & 0xFFFF_FFFFL;
        long y1 = y >> 32;
        long y2 = y & 0xFFFF_FFFFL;

        long r = x2 * y2;
        long r1 = r >>> 32;

        long t = x1 * y2 + r1;
        long t1 = t >> 32;
        long t2 = t & 0xFFFF_FFFFL;

        long s = t2 + x2 * y1;

        return x1 * y1 + t1 + (s >> 32);
    }

    /*
     * ------------------------------------------
     * 符号無し long 乗算アルゴリズム
     * ------------------------------------------
     * 
     * [言語仕様上の注意]
     * 符号なし整数どうしの和・積は, 符号なしの意味でオーバーフローしない限り,
     * ビットパターンは符号ありと同一の結果となる.
     * 
     * x,y を符号無し 64-bit と見なし,
     * x = x1 * (2^32) + x2,
     * y = y1 * (2^32) + y2
     * となる x1, x2, y1, y2 を計算する.
     * ただし, x1, y1, x2, y2 は符号無し 32-bit (0 以上 2^32 - 1 以下) である.
     * これは, (x >>> 32) や ( x & 0xFFFF_FFFFL) で実現.
     * x*y = (x1*y1)*(2^64) + (x1*y2 + x2*y1)*(2^32) + (x2*y2)
     * である.
     * 
     * 符号無し 32-bit と符号無し 32-bit の積は 0 以上, 2^64 - 1 - 2^32 以下であるので,
     * x1*y2, x2*y1 に2^32 以下の値を加えても (符号無し 64-bit としては)
     * オーバーフローを起こさないという性質をこの後に使う.
     * 
     * x2*y2 = r1 * (2^32) + r2 とし (上位 32-bit と下位 32-bit に分け),
     * t = x1*y2 + r1 とすれば,
     * x*y = (x1*y1)*(2^64) + (t + x2*y1)*(2^32) + r2
     * となり, さらに t = t1 * (2^32) + t2 とすれば,
     * x*y = (x1*y1 + t1)*(2^64) + (t2 + x2*y1)*(2^32) + r2
     * となる.
     * s = t2 + x2*y1 = s1 * (2^32) + s2 とすれば,
     * x*y = (x1*y1 + t1 + s1)*(2^64) + s2 * (2^32) + r2
     * となる.
     * 
     * 確認として, r1 = r >>> 32, t1 = t >>> 32, s1 = s >>> 32 である.
     */

    /**
     * 内部から呼ばれる. <br>
     * 64bit整数 <i>x</i>, <i>y</i> について,
     * <i>x</i><i>y</i> を符号無し128bitで計算した結果を計算し, 引数の配列に格納する.
     * 
     * @param x x
     * @param y y
     * @param result 結果格納用, {上位, 下位}, サイズ2でなければならない
     */
    private static void unsignedMultiplyFullLongHelper(long x, long y, long[] result) {

        /* 符号無し long 乗算アルゴリズムに従う. */

        long x1 = x >>> 32;
        long x2 = x & 0xFFFF_FFFFL;
        long y1 = y >>> 32;
        long y2 = y & 0xFFFF_FFFFL;

        long r = x2 * y2;
        long r1 = r >>> 32;

        long t = x1 * y2 + r1;
        long t1 = t >>> 32;
        long t2 = t & 0xFFFF_FFFFL;

        long s = t2 + x2 * y1;

        result[0] = x1 * y1 + t1 + (s >>> 32);
        result[1] = (s << 32) | (r & 0xFFFF_FFFFL);
    }

    /**
     * 64bit整数 <i>x</i>, <i>y</i> を符号無しと見なし,
     * <i>x</i><i>y</i> を128bitで計算した結果を計算して, 配列として返す.
     * 
     * <p>
     * 結果は {@code long} の配列として返される. <br>
     * 配列はサイズ2であり, 第0要素に上位bit, 第1要素に下位bitが格納されている.
     * </p>
     * 
     * @param x <i>x</i> (符号無しと見なす)
     * @param y <i>y</i> (符号無しと見なす)
     * @return <i>x</i><i>y</i> の128bit, {上位64bit, 下位64bit}
     */
    public static long[] unsignedMultiplyFullLong(long x, long y) {
        long[] out = new long[2];
        unsignedMultiplyFullLongHelper(x, y, out);
        return out;
    }

    /**
     * 64bit整数 <i>x</i>, <i>y</i> を符号無しと見なし,
     * <i>x</i><i>y</i> を128bitで計算した結果を計算し, 引数の配列に格納する.
     * 
     * <p>
     * 結果は引数の {@code long} の配列に格納される. <br>
     * 第0要素に上位bit, 第1要素に下位bitが格納されている. <br>
     * 配列はサイズ2でなければならない.
     * </p>
     * 
     * @param x <i>x</i> (符号無しと見なす)
     * @param y <i>y</i> (符号無しと見なす)
     * @param result <i>x</i><i>y</i> の128bitを格納するための配列,
     *            {上位64bit, 下位64bit} が格納される.
     * @throws IllegalArgumentException 引数の配列がサイズ2でない場合
     * @throws NullPointerException 引数にnullが含まれる場合
     */
    public static void unsignedMultiplyFullLong(long x, long y, long[] result) {
        if (result.length != 2) {
            throw new IllegalArgumentException(
                    "illegal: result.length != 2: length = " + result.length);
        }
        unsignedMultiplyFullLongHelper(x, y, result);
    }

    /**
     * 64bit整数 <i>x</i>, <i>y</i> を符号無しと見なし,
     * <i>x</i><i>y</i> を128bitで計算したときの上位64bitを計算する.
     * 
     * <p>
     * 下位64bitを計算するメソッド {@code unsignedMultiplyLowLong} は提供していない. <br>
     * 下位64bitは {@code long} の通常の積を {@code long} 型で受け取ることで得られる.
     * </p>
     * 
     * @param x <i>x</i> (符号無しと見なす)
     * @param y <i>y</i> (符号無しと見なす)
     * @return <i>x</i><i>y</i> の上位64bit
     */
    public static long unsignedMultiplyHighLong(long x, long y) {

        /* 符号無し long 乗算アルゴリズムに従う. */

        long x1 = x >>> 32;
        long x2 = x & 0xFFFF_FFFFL;
        long y1 = y >>> 32;
        long y2 = y & 0xFFFF_FFFFL;

        long r = x2 * y2;
        long r1 = r >>> 32;

        long t = x1 * y2 + r1;
        long t1 = t >>> 32;
        long t2 = t & 0xFFFF_FFFFL;

        long s = t2 + x2 * y1;

        return x1 * y1 + t1 + (s >>> 32);
    }
}
