package ru.andrw.java.chat.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by john on 7/1/2016.
 */
public class MyClass {
    public static void myMethod() {
        Arrays.asList("One", "Two", "Three")
                .stream().map(String::toUpperCase)
                .forEach(System.out::println);
    }
}
