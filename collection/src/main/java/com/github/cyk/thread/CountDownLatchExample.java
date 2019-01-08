package com.github.cyk.thread;

import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class CountDownLatchExample {

    /**
     * countdownlatch 主要是两个方法 countDown() 和await();  countDown() 用于计数器减1 ，一般执行任务的线程调用，await() 使线程处于等待状态，一把主线程调用
     * countDown()方法并没有规定一个线程只能调用一次，当同一个线程调用多次countDown()方法时，每次都会使计数器减一；另外，await()方法也并没有规定只能有一个线程执行该方法，如果多个线程同时执行await()方法，那么这几个线程都将处于等待状态，并且以共享模式享有同一个锁
     *
     * CountDownLatch和CyclicBarrier都能够实现线程之间的等待，只不过它们侧重点不同：
     *
     * CountDownLatch一般用于某个线程A等待若干个其他线程执行完任务之后，它才执行；
     *
     * 而CyclicBarrier一般用于一组线程互相等待至某个状态，然后这一组线程再同时执行；
     *
     * 另外，CountDownLatch是不能够重用的，而CyclicBarrier是可以重用的。
     *
     * 2）Semaphore其实和锁有点类似，它一般用于控制对某组资源的访问权限。
     *
     */

    final CountDownLatch latch = new CountDownLatch(2);

    @Test
    public void testCountDownLatch(){

        new Thread(){
            @Override
            public void run(){
                try {
                    System.out.println("子线程1"+Thread.currentThread().getName()+"正在执行");
                    Thread.sleep(3000);
                    System.out.println("子线程1"+Thread.currentThread().getName()+"执行完毕");
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();


        new Thread(){
            @Override
            public void run(){
                try {
                    System.out.println("子线程2"+Thread.currentThread().getName()+"正在执行");
                    Thread.sleep(3000);
                    System.out.println("子线程2"+Thread.currentThread().getName()+"执行完毕");
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();


        try {
            System.out.println("等待2个子线程执行完毕..."+Thread.currentThread().getName());
            latch.await();
            System.out.println("2个子线程已经执行完毕"+Thread.currentThread().getName());
            System.out.println("继续执行主线程:"+Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *  cyclicbarrier 可以重复 而CountDownLatch无法进行重复使用
     * @throws Exception
     */

    @Test
    public void testCyclicBarrier()throws Exception{
        int N = 4;
        //CyclicBarrier barrier  = new CyclicBarrier(4);

        // 所有线程执行完成后，操作,任意选择一个去执行
        CyclicBarrier barrier  = new CyclicBarrier(N,new Runnable() {
            @Override
            public void run() {
                System.out.println("当前线程"+Thread.currentThread().getName());
            }
        });
        for(int i=0;i<N;i++){
            new Writer(barrier).start();
        }
    }

    static class Writer extends Thread{
        private CyclicBarrier cyclicBarrier;
        public Writer(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println("线程"+Thread.currentThread().getName()+"正在写入数据...");
            try {
                //以睡眠来模拟写入数据操作
                //Thread.sleep(1000);
                System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕，等待其他线程写入完毕");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }catch(BrokenBarrierException e){
                e.printStackTrace();
            }
            System.out.println("所有线程写入完毕，继续处理其他任务...");
        }
    }

    @Test
    public void testSemaphore(){
        int N = 8;            //工人数
        Semaphore semaphore = new Semaphore(5); //机器数目
        for(int i=0;i<N;i++) {
            new Worker(i, semaphore).start();
        }
    }

    static class Worker extends Thread{
        private int num;
        private Semaphore semaphore;
        public Worker(int num,Semaphore semaphore){
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println("工人"+this.num+"占用一个机器在生产...");
                //Thread.sleep(2000);
                System.out.println("工人"+this.num+"释放出机器");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
