package com.github.cyk.collection;

import com.google.common.util.concurrent.*;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.FutureListener;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.function.Supplier;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class Async {

    /**
     *  Java5 并发库主要关注异步任务处理 producer创建任务，利用阻塞队列将任务传递给 consumer
     *  Java7/8 将任务分解为子集，每个子集可以独立且同质的子任务负责处理
     *  fork/join 框架，对数据集进行分割，提交到默认线程池中，ForkJoinPool ,处理前提条件 数据量足够大，每个元素处理成本足够高，才能补偿fork/join 框架消耗成本
     *  Async 类
     */


    /**
     * 异步计算 回调函数
     *
     * Jdk5 Future 接口 ,用户描述一个异步计算结果，获取异步结果不方便，需要阻塞或轮询 阻塞与异步理念违背，轮询过于消耗 CPU资源
     *
     * 使用观察者模式  计算完成及时通知监听者
     *
     *  netty  channelFuture  addlistener
     */


    @Test
    public void completedFutureExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message");
        assertTrue(cf.isDone());
        assertEquals("message", cf.getNow(null));
    }


    @Test
    public void runAsyncExample()throws Exception {
        CompletableFuture<Void>cf = CompletableFuture.runAsync(() -> {
            assertTrue(Thread.currentThread().isDaemon());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        assertFalse(cf.isDone());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //assertTrue(cf.isDone());
    }

    @Test
    public void beforeJdk8() throws Throwable, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        // Future代表了线程执行完以后的结果，可以通过future获得执行的结果
        // 但是jdk1.8之前的Future有点鸡肋，并不能实现真正的异步，需要阻塞的获取结果，或者不断的轮询
        // 通常我们希望当线程执行完一些耗时的任务后，能够自动的通知我们结果，很遗憾这在原生jdk1.8之前
        // 是不支持的，但是我们可以通过第三方的库实现真正的异步回调
        Future<String> f = executor.submit(new Callable<String>() {

            @Override
            public String call() throws Exception {
                System.out.println("task started!");
                //Thread.sleep(3000);
                System.out.println("task finished!");
                return "hello";
            }
        });

        //此处阻塞main线程
        System.out.println("==="+f.get());
        System.out.println("main thread is blocked");
    }


    public void guavaFuture(){
        ExecutorService executor = Executors.newFixedThreadPool(1);
        // 使用guava提供的MoreExecutors工具类包装原始的线程池
        ListeningExecutorService listeningExecutor = MoreExecutors.listeningDecorator(executor);
        //向线程池中提交一个任务后，将会返回一个可监听的Future，该Future由Guava框架提供
        ListenableFuture<String> lf = listeningExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("task started!");
                //模拟耗时操作
                Thread.sleep(3000);
                System.out.println("task finished!");
                return "hello";
            }
        });
        //添加回调，回调由executor中的线程触发，但也可以指定一个新的线程
        Futures.addCallback(lf, new FutureCallback<String>() {
            //耗时任务执行失败后回调该方法
            @Override
            public void onFailure(Throwable t) {
                System.out.println("failure");
            }
            //耗时任务执行成功后回调该方法
            @Override
            public void onSuccess(String s) {
                System.out.println("success " + s);
            }
        });
        //主线程可以继续做其他的工作
        System.out.println("main thread is running");
    }


    public void nettyFuture(){
        //线程池
        EventExecutorGroup group = new DefaultEventExecutorGroup(1);
        //向线程池中提交任务，并返回Future，该Future是netty自己实现的future
        //位于io.netty.util.concurrent包下，此处运行时的类型为PromiseTask
        Future<?> ff = group.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("任务正在执行");
                //模拟耗时操作，比如IO操作
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("任务执行完毕");
            }
        });

        //增加监听
        ((io.netty.util.concurrent.Future<?>) ff).addListener(new FutureListener() {
            @Override
            public void operationComplete(io.netty.util.concurrent.Future future) throws Exception {
                System.out.println("ok!!!");
            }
        });
        System.out.println("main thread is running.");
    }


    public void jdk8Future(){
        //两个线程的线程池
        ExecutorService executor = Executors.newFixedThreadPool(2);
        //jdk1.8之前的实现方式
        CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                System.out.println("task started!");
                try {
                    //模拟耗时操作
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "task finished!";
            }
        }, executor);

        //采用lambada的实现方式
        future.thenAccept(e -> System.out.println(e + " ok"));

        System.out.println("main thread is running");

    }




}
