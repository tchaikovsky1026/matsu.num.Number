/*
 * Copyright © 2025 Matsuura Y.
 * 
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package matsu.num.number.speedutil;

import java.util.Objects;

/**
 * 計算時間評価を行う仕組みを扱う.
 * 
 * @author Matsuura Y.
 */
public final class SpeedTestExecutor {

    private final Class<?> testClass;
    private final Object testInstance;

    private final int iteration;
    private final Runnable runner;

    /**
     * エグゼキュータを構築する.
     * 
     * @param testClass テストクラス, コンソールへの表示で使う. nullでもよい (表示されなくなる).
     * @param testInstance テストするインスタンス, コンソールへの表示で使う. nullでもよい (表示されなくなる).
     * @param iteration 繰り返し回数, 1以上
     * @param runner 1回の繰り返しの中で行う処理
     * @throws IllegalArgumentException iterationが不適
     * @throws NullPointerException ランナーがnull
     */
    public SpeedTestExecutor(Class<?> testClass, Object testInstance, int iteration, Runnable runner) {
        super();
        this.testClass = testClass;
        this.testInstance = testInstance;
        this.iteration = iteration;
        this.runner = Objects.requireNonNull(runner);

        if (iteration <= 0) {
            throw new IllegalArgumentException("iterationが不適");
        }
    }

    /**
     * テストを実行する.
     */
    public void execute() {
        System.gc();

        if (Objects.nonNull(testClass)) {
            System.out.println(testClass.getName() + ": speed measurement");
        }
        if (Objects.nonNull(testInstance)) {
            System.out.println(testInstance);
        }

        for (int c = 0; c < 5; c++) {
            long startMills = System.nanoTime();
            for (int i = 0; i < iteration; i++) {
                runner.run();
            }
            long endMills = System.nanoTime();
            System.out.println(
                    "%.3f ns".formatted((double) (endMills - startMills) / iteration));
        }

        System.out.println();
    }
}
