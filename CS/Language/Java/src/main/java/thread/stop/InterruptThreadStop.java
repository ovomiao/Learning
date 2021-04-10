package thread.stop;

/**
 * 推荐的停止线程的做法
 *  * 使用中断标识，由线程的 run 方法内部配合，可在合适的时机中断线程
 *      * public void interrupt()
 *          * 为指定线程设置一个中断标记，只是设置一个中断标记，不会立即中止目标线程
 *             是否中止目标线程，需要目标线程配合，这个方法只是一个通知而已。
 *  * 线程自己判断自己是否处于中断状态的两个方法的
 *      * Thread#isInterrupted()
 *          * 这个方法返回当前线程是否处于中断状态。（一般不用）
 *              * 如果当前线程被设置中断状态，那么此方法会一直返回 true
 *      * Thread.interrupted() 返回当前线程的中断状态，并重置当前线程的中断状态
 *          * 例如：其他线程把当前线程的中断状态设为 true；那么调用此方法将返回 true
 *                  同时将自己的中断状态重置为 false
 */

class MyInterruptThread implements Runnable {
    @Override
    public void run() {
        int i = 0;
        try {
            // 由于这里是可以获取当前线程是否有中断标记，
            // 所以何时停止线程，我们可以自己控制
            while (true) {
                // 我是中断状态，我现在可以结束我自就了
                if (Thread.interrupted()) {
                    System.out.println("我要结束了");
                    System.out.println("我来做一些收尾工作，马上就结束！");
                    return;
                }
                System.out.printf("开始修改数据...");
                System.out.printf("i = " + i ++);
                Thread.sleep(0);
                System.out.printf("本次数据修改完毕");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            // 收尾工作
            return;
        }
    }
}

public class InterruptThreadStop {
    public static void main(String[] args)  {
        Thread thread = new Thread(new MyInterruptThread());
        thread.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.printf(">>>>");
        }

        // 为目标线程设置一个中断标记
        thread.interrupt();
    }
}
