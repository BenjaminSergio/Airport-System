package com.solvd.airport;

import com.solvd.airport.utils.ThreadUtils;
import com.solvd.airport.utils.ThreadsExtended;
import com.solvd.airport.utils.ThreadRunnable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static{
        System.setProperty("log4j.configurationFile","src/main/resources/log4j2.xml");
    }

    public static final Logger LOGGER = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        //Debilitating the airport system to test the threads
        //AirportSystem.functionalityTest();

        //Thread testing
        ThreadsExtended threadsExtended = new ThreadsExtended();
        ThreadRunnable threadRunnable = new ThreadRunnable();
        //Passing a Runnable through a thread constructor
//        Thread thread = new Thread(threadRunnable);
//        threadRunnable.run();
//        threadsExtended.start();
//        thread.start();

        ThreadUtils threadUtils = new ThreadUtils();
        /*
        try {
            threadUtils.runWorkers();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
*/
        threadUtils.runFutureWorkers();
        LOGGER.warn("Compilation success!, view more in debug.log");
    }
}