package com.tn.web;

import lombok.SneakyThrows;

import java.lang.reflect.Constructor;

public class BacksonMapper {
    public static void main(String[] args) {

    }

/*    @SneakyThrows
    public static <T> T readObject(String json, Class<T> classT) {
        String[] lines = json.split(",");

        Constructor<T> constructor = classT.getConstructor();
        T object = constructor.newInstance();

        for (String line : lines) {

        }



    }*/
}
