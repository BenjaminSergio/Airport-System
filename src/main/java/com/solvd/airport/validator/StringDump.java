package com.solvd.airport.validator;

import com.solvd.airport.annotation.Sensitive;

import java.lang.reflect.RecordComponent;
import java.util.StringJoiner;

public class StringDump {

    public static String dump(Object obj){
        Class<?> clazz = obj.getClass();
        StringJoiner out = new StringJoiner(", ", clazz.getSimpleName()+"[","]");

        for(RecordComponent rc: clazz.getRecordComponents()){
            try {
                Object raw = rc.getAccessor().invoke((obj));
                boolean secret = rc.isAnnotationPresent(Sensitive.class);
                out.add(rc.getName()+ "=" + (secret ? "******" : raw));

            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }

        }

        return out.toString();
    }

}
