package com.dqcer.dxptoolsspring;

import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/**
 * @author dongqin
 * @description 秒表测试
 * @date 2021/08/23
 */
public class StopWatchTests {

    private final StopWatch stopWatch = new StopWatch("dqcer");

    @Test
    void test() {
        stopWatch.start("task 1");
        //  执行methodsA()
        methodsA();
        stopWatch.stop();

        stopWatch.start("task 2");
        // 执行methodsA()
        methodsA();
        stopWatch.stop();
        System.out.println("getId" + stopWatch.prettyPrint());
    }

    public static void methodsA() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 失败之前开始计时
     */
    @Test
    void failureToStartBeforeGettingTimings() throws InterruptedException {

        stopWatch.start("123");
        System.out.println("ddddddd");
        Thread.sleep(2000);
        stopWatch.stop();
        stopWatch.start("345");
        System.out.println("ddddddd");
        Thread.sleep(2000);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }


    /**
     * 未能启动之前停止
     */
    @Test
    void failureToStartBeforeStop() {
        assertThatIllegalStateException().isThrownBy(stopWatch::stop);
    }

    /**
     * 拒绝开始的两倍
     */
    @Test
    void rejectsStartTwice() {
        stopWatch.start();
        assertThat(stopWatch.isRunning()).isTrue();
        stopWatch.stop();
        assertThat(stopWatch.isRunning()).isFalse();

        stopWatch.start();
        assertThat(stopWatch.isRunning()).isTrue();
        assertThatIllegalStateException().isThrownBy(stopWatch::start);

        System.out.println(stopWatch.prettyPrint());
    }


    private static long millisToNanos(long duration) {
        return MILLISECONDS.toNanos(duration);
    }

}
