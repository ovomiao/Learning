package thread.stop;

/**
 * 线程的交互：
 *      * 一个线程启动另一个线程
 *      * 一个线程中止另一个线程
 *          * stop() 中止线程的缺点：
 *              * 强制中断了线程，不关注正在执行的线程的状态。
 */

class MyThread implements Runnable {

    @Override
    public void run() {
        int i = 0;
        while (true) {
            try {
                System.out.printf("正在修改数据...");
                System.out.println("线程正在运行：i = " +  i++);
                Thread.sleep(500);
                // 使用 stop 强制停止线程，可能会导致各种操作进行到一半，被强制
                // 结束了；例如：写文件写一半线程结束导致文件丢失。
                System.out.printf("本次数据修改完成！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

public class StopThreadTest {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new MyThread());
        thread.start();

        Thread.sleep(5000);

        // 强制中断线程：这是不可空操作，只要调用此方法，那么目标线程将被
        // 立即中止。
        thread.stop();
    }
}

