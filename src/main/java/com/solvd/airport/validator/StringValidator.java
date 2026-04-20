package com.solvd.airport.validator;

import com.solvd.airport.annotation.StringValidation;

import java.lang.reflect.Field;

public class StringValidator {
    public static void stringValidate(Object obj) throws IllegalAccessException{
        Class clazz = obj.getClass();
        for(Field field : clazz.getDeclaredFields()){
            if(field.isAnnotationPresent(StringValidation.class)){
                field.setAccessible(true);
                StringValidation validation = field.getAnnotation(StringValidation.class);
                Object value = field.get(obj);

                if(!validation.allowNull() && value == null) throw new RuntimeException(field.getName()+" Cannot be NULL");

                if(!validation.allowEmpty() && value instanceof String && ((String) value).isEmpty()) throw new RuntimeException(field.getName()+" Cannot be Empty");
            }
        }
    }
    public static void stringValidateAll(Object obj) throws IllegalAccessException {
        Class clazz = obj.getClass();
        for(Field field : clazz.getDeclaredFields()){
            field.setAccessible(true);
            Object value = field.get(obj);

            if(field.getType().equals(String.class)){
                if(value == null) throw new RuntimeException(field.getName()+" Cannot be NULL");
                if(((String) value).isEmpty()) throw new RuntimeException(field.getName()+" Cannot be Empty");
            }

        }
    }
}
