package com.solvd.airport.utils;

import static com.solvd.airport.Main.LOGGER;

public class ThreadRunnable implements  Runnable{

    @Override
    public void run() {

        for(int i = 0; i < 10; i++) {
            LOGGER.info("Thread :{}", Thread.currentThread().getName());
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
