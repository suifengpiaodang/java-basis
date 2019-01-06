package com.github.cyk.collection;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

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
           /* try {
                //Thread.sleep( Long.parseLong(Integer.parseInt(String.valueOf(Math.random()* 1000)).));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        });
        assertFalse(cf.isDone());
        /*try {
            Thread.sleep( Long.parseLong(String.valueOf(Math.random()* 1000 * 1000)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        assertTrue(cf.isDone());
    }


}
