package service;

import com.bbongdoo.doo.service.MyCounter;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.assertj.core.api.Assertions;

public class ThreadTest {
    @DisplayName("FixedThreadPool 을 생성한다.")
    @Test
    void testCounterWithConcurrencyFixed() throws InterruptedException {
        int numberOfThreads = 18;
        ExecutorService service = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        MyCounter counter = new MyCounter();
        iterateThread(numberOfThreads, service, latch, counter);

        Assertions.assertThat(((ThreadPoolExecutor) service).getPoolSize()).isEqualTo(5);
    }

    private void iterateThread(int numberOfThreads, ExecutorService service, CountDownLatch latch, MyCounter counter) throws InterruptedException {
        for (int i = 0; i < numberOfThreads; i++) {
            service.submit(() -> {
                counter.increment();
                latch.countDown();
                throw new IllegalArgumentException();
            });
        }
        latch.await();
    }

}
