package com.bbongdoo.doo.service;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExcutorService {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for(int i = 0; i < 10; i++){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;
                    int poolSize = threadPoolExecutor.getPoolSize();
                    String threadName = Thread.currentThread().getName();

                    System.out.println("[총 스레드 개수:" + poolSize + "] 작업 스레드 이름: "+threadName);
                    int value = Integer.parseInt("윤호");
                }
            };

            //스레드풀에게 작업 처리 요청
//            executorService.execute(runnable);
            executorService.submit(runnable);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }

}
