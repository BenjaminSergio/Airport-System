package com.solvd.airport.utils;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionPool {
    private static final int POOL_SIZE = 5;
    private final BlockingDeque<Connection> free =
            new LinkedBlockingDeque<>(POOL_SIZE);

    private ConnectionPool(){
        for(int i = 0; i < POOL_SIZE; i++) {
            Connection c = new Connection();
            c.open();
            free.offer(c);
        }
    }

    private static class Holder{
        static final ConnectionPool INSTANCE = new ConnectionPool();
    }
    public static ConnectionPool getInstance(){return Holder.INSTANCE;}

    public Connection acquire() throws InterruptedException{
        return free.take();
    }
    public void shutdown(){
        for(Connection c : free) c.close();
        free.clear();
    }
}
