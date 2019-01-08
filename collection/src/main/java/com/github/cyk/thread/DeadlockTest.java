package com.github.cyk.thread;

import java.util.concurrent.TimeUnit;

public class DeadlockTest {

    public static void main(String[] args) {
        Object lockA = new Object();
        Object lockB = new Object();
        new Thread(() -> {
            //线程1获得A锁
            synchronized (lockA) {
                System.out.println("Thread 1 get lock A .");
                //增加死锁发生几率
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                }
                //尝试获得B锁
                System.out.println("Thread 1 try get lock B, waiting...");
                synchronized (lockB) {
                    //永远无法获得B锁，所以不会打印出来
                    System.out.println("Thread 1 get lock B .");
                }

            }
        }, "Thread 1").start();
        new Thread(() -> {
            //线程2获得B锁
            synchronized (lockB) {
                System.out.println("Thread 2 get lock B .");
                //增加死锁发生几率
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                }
                //尝试获得A锁
                System.out.println("Thread 2 try get lock A, waiting...");
                synchronized (lockA) {
                    //永远无法获得A锁，所以不会打印出来
                    System.out.println("Thread 2 get lock A .");
                }

            }
        }, "Thread 2").start();

    }
}