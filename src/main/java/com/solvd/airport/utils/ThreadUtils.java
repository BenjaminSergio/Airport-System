package com.solvd.airport.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.solvd.airport.Main.LOGGER;

public class ThreadUtils {

    ConnectionPool pool = ConnectionPool.getInstance();
    ExecutorService workers = Executors.newFixedThreadPool(7);

    List<CompletableFuture<Void>> stages = new ArrayList<>();

    Runnable task = () -> {
        try{
            String name = Thread.currentThread().getName();
            LOGGER.info("{} Waiting for Connection",name);
            Connection c = pool.acquire();
            LOGGER.info("{} got {}", name, c);
            Thread.sleep(2_000);
            LOGGER.info("{} Released {}",name , c);
            pool.release(c);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    };

    public void runWorkers() throws InterruptedException {
        for(int i = 0; i < 7; i++) workers.submit(task);
        workers.shutdown();
        if (workers.awaitTermination(10, TimeUnit.SECONDS)) workers.shutdownNow();
        pool.shutdown();
    }

    public void runFutureWorkers () {
        for(int i = 0; i < 7; i++){
            int finalI = i;
            CompletableFuture<Void> stage = CompletableFuture
                    .supplyAsync(()-> {
                        try{
                            String name = Thread.currentThread().getName();
                            LOGGER.info("{} Waiting for Connection",name);
                            Connection c = pool.acquire();
                            LOGGER.info("{} got {}", name, c);
                            Thread.sleep(2_000);
                            return c;
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    },workers)
                    .thenAccept(c ->{
                        LOGGER.info("{} Released {}", finalI, c);
                        pool.release(c);
                    });
            stages.add(stage);
        }
        CompletableFuture.allOf(stages.toArray((new CompletableFuture[0]))).join();
        workers.shutdownNow();
        pool.shutdown();
    }

}
