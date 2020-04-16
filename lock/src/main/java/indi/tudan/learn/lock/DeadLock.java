package indi.tudan.learn.lock;

/**
 * 模拟死锁
 *
 * @author wangtan
 * @date 2020-04-16 19:24:22
 * @since 1.0
 */
public class DeadLock {

    public static final String resource1 = "resource1";
    public static final String resource2 = "resource2";

    public static void main(String[] args) {
        Thread thread1 = new Thread(new BusinessA());
        Thread thread2 = new Thread(new BusinessB());
        thread1.start();
        thread2.start();
    }

    static class BusinessA implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("BusinessA启动");
                synchronized (DeadLock.resource1) {
                    System.out.println("BusinessA拿到了resource1的锁");
                    // 获取resource1后先等一会儿，让BusinessB有足够的时间锁住resource2
                    Thread.sleep(3000);
                    System.out.println("BusinessA想拿resource2的锁。。。。");
                    synchronized (DeadLock.resource2) {
                        System.out.println("BusinessA获得到了resource2的锁");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class BusinessB implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("BusinessB启动");
                synchronized (DeadLock.resource2) {
                    System.out.println("BusinessB拿得到了resource2的锁");
                    // 获取resource2后先等一会儿，让BusinessA有足够的时间锁住resource1
                    Thread.sleep(3000);
                    System.out.println("BusinessB想拿resource1的锁。。。。");
                    synchronized (DeadLock.resource1) {
                        System.out.println("BusinessB获得到了resource1的锁");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
