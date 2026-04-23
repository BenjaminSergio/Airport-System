package com.solvd.airport.utils;

import static com.solvd.airport.Main.LOGGER;
public class ThreadsExtended extends Thread{
    @Override
    public void run(){
        for(int i = 0; i < 10; i++) {
            LOGGER.info("Thread :{}", getName());
            try {
                sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
