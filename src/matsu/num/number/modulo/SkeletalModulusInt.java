/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */

/*
 * 2025.8.8
 */
package matsu.num.number.modulo;

/**
 * {@link ModulusInt} の骨格実装.
 * 
 * @author Matsuura Y.
 */
abstract class SkeletalModulusInt implements ModulusInt {

    /**
     * 唯一のコンストラクタ.
     */
    SkeletalModulusInt() {
        super();
    }

    /**
     * このインスタンスの文字列表現を返す.
     * 
     * <p>
     * 文字列表現は明確に規定されておらず, バージョン間の互換性も担保されていない. <br>
     * おそらく次のような形式だろう. <br>
     * {@code %InterfaceName(divisor = %divisor) }
     * </p>
     */
    @Override
    public String toString() {
        return "%s(divisor = %s)"
                .formatted(ModulusInt.class.getSimpleName(), this.divisor());
    }
}
