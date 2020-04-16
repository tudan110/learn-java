package indi.tudan.learn.lock;

import cn.hutool.core.lang.Console;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 乐观锁 CAS，产生了 ABA 问题
 *
 * @author wangtan
 * @date 2020-04-16 16:30:16
 * @since 1.0
 */
public class CAS {

    private static final AtomicInteger index = new AtomicInteger(10);

    public static void main(String[] args) {

        new Thread(() -> {

            index.compareAndSet(10, 11);
            index.compareAndSet(11, 10);
            Console.log("{}: 10 -> 11 -> 10", Thread.currentThread().getName());

        }, "线程 1").start();

        new Thread(() -> {
            try {

                TimeUnit.SECONDS.sleep(2);
                boolean success = index.compareAndSet(10, 12);
                Console.log("{}: index {}预期的值，新值是: {}",
                        Thread.currentThread().getName(),
                        success ? "是" : "不是",
                        index.get());
                if (success) {
                    Console.log("产生了 ABA 问题。");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "线程 2").start();

    }

}
