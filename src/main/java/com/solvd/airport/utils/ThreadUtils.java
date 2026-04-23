package com.solvd.airport.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.solvd.airport.Main.LOGGER;

public class ThreadUtils {

    ConnectionPool pool = ConnectionPool.getInstance();
    ExecutorService workers = Executors.newFixedThreadPool(7);

    Runnable task = () -> {
        try{
            String name = Thread.currentThread().getName();
            LOGGER.info("{} Waiting for Connection",name);
            Connection c = pool.acquire();
            LOGGER.info("{} got {}", name, c);
            Thread.sleep(2_000);
            LOGGER.info("{} Released {}",name , c);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    };

    public void runWorkers() throws InterruptedException {
        for(int i = 0; i < 7; i++) workers.submit(task);
        workers.awaitTermination(10, TimeUnit.SECONDS);
        pool.shutdown();
    }

}
