package indi.tudan.learn.lock;

import cn.hutool.core.lang.Console;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * CAS 解决 ABA 问题
 *
 * @author wangtan
 * @date 2020-04-16 17:10:45
 * @since 1.0
 */
public class CASSolvedABA {

    private static final AtomicInteger index = new AtomicInteger(10);

    static AtomicStampedReference<Integer> stampRef = new AtomicStampedReference<>(10, 1);

    public static void main(String[] args) {

        new Thread(() -> {
            int stamp = stampRef.getStamp();
            Console.log("{}: 第一次版本号 {}", Thread.currentThread().getName(), stamp);

            stampRef.compareAndSet(10, 11, stampRef.getStamp(), stampRef.getStamp() + 1);
            Console.log("{}: 第二次版本号 {}", Thread.currentThread().getName(), stampRef.getStamp());

            stampRef.compareAndSet(11, 10, stampRef.getStamp(), stampRef.getStamp() + 1);
            Console.log("{}: 第三次版本号 {}", Thread.currentThread().getName(), stampRef.getStamp());

        }, "线程 1").start();

        new Thread(() -> {
            try {

                int stamp = stampRef.getStamp();
                Console.log("{}: 第一次版本号 {}", Thread.currentThread().getName(), stamp);

                TimeUnit.SECONDS.sleep(2);

                boolean success = stampRef.compareAndSet(10, 12, stampRef.getStamp(), stampRef.getStamp() + 1);
                Console.log("{}: 第二次版本号 {}", Thread.currentThread().getName(), stampRef.getStamp());

                Console.log("{}: index 修改是否成功 {}，当前值：{}，当前版本: {}。",
                        Thread.currentThread().getName(),
                        success,
                        stampRef.getReference(),
                        stampRef.getStamp());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "线程 2").start();

    }

}
