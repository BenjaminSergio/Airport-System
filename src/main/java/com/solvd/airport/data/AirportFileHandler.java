package com.solvd.airport.data;

import com.solvd.airport.records.Ticket;
import com.solvd.airport.utils.AirportUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

import static com.solvd.airport.Main.LOGGER;

public abstract class AirportFileHandler {
    public static void addTicketToFile(String filePath, Ticket ticket){
        File file = new File(filePath);
        String entry = ticket.toString()+"\n";
        try{
            FileUtils.writeStringToFile(file,entry, StandardCharsets.UTF_8,true);
            LOGGER.info("Entry successfully registered");
        } catch (IOException e) {
            LOGGER.warn(e);
        }
    }
    public static void countTicketsInFile(String filePath){
        File souceFile = new File(filePath);
        File logFile = new File(AirportUtils.DEFAULT_TICKET_COUNT_PATH);
        String file = "";
        if(souceFile.exists()){
            try {
                file = FileUtils.readFileToString(souceFile, StandardCharsets.UTF_8);
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
        int totalTickets = StringUtils.countMatches(file,"Ticket");
        String log = "Tickets found in the file: "+totalTickets;
        try {
            FileUtils.writeStringToFile(logFile,log,StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
