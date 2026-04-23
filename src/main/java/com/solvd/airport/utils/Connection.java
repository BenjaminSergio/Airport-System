package com.solvd.airport.utils;

import java.util.concurrent.atomic.AtomicInteger;

public final class Connection{
    private static final AtomicInteger IDS = new AtomicInteger();
    private final int id = IDS.incrementAndGet();

    public void open(){}
    public void close(){}

    @Override
    public String toString(){return "Connection#"+id;}
}
