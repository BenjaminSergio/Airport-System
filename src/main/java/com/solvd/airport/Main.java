package com.solvd.airport;

import com.solvd.airport.systens.AirportSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static{
        System.setProperty("log4j.configurationFile","src/main/resources/log4j2.xml");
    }

    public static final Logger LOGGER = LogManager.getLogger(Main.class);
    /*  Usage Cases // Because of the way info is set, it only shows in the console if its warn or more
        LOGGER.info("This is a log info message");
        LOGGER.warn("This is a log warn message");
        LOGGER.debug("Debug");*/

    public static void main(String[] args) {
        AirportSystem.functionalityTest();
        LOGGER.warn("Compilation success!, view more in debug.log");
    }
}