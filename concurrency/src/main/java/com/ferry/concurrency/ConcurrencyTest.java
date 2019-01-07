package com.ferry.concurrency;

import com.ferry.concurrency.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 并发测试
 *
 * @author Ferry NLP
 * @create 2018-12-29
 * @since 1.0v
 **/
@Slf4j
@NotThreadSafe//不安全的
public class ConcurrencyTest {

    // 请求总数
    public static int clientTotal = 5000;
    // 同时并发执行的线程数
    public static int threadTotal =200;

    public static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore sampleModel = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0 ;i < clientTotal ;i++) {
            executorService.execute(()->{
                try {
                    sampleModel.acquire();
                    add();
                    sampleModel.release();
                } catch (InterruptedException e) {
                    //System.error();
                    System.out.println("exception:"+e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        //log.info("count:{}", count);
        System.out.println("count:{"+count+"}");

    }

    private static void add(){
        count++;
    }
}
