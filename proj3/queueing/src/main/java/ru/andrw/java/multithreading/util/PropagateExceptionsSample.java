package ru.andrw.java.multithreading.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

/**
 * Created by john on 7/6/2016.
 */
public class PropagateExceptionsSample {
    // a simplified version of Throwables#propagate
    public static RuntimeException runtime(Throwable e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException)e;
        }

        return new RuntimeException(e);
    }

    // this is a new one, n/a in public libs
    // Callable just suits as a functional interface in JDK throwing Exception
    public static <V> V propagate(Callable<V> callable){
        try {
            return callable.call();
        } catch (Exception e) {
            throw runtime(e);
        }
    }

    public static void main(String[] args) {
        class Account{
            String name;
            Account(String name) { this.name = name;}

            public boolean isActive() throws IOException {
                return name.startsWith("a");
            }
        }


        List<Account> accounts = new ArrayList<>(Arrays.asList(new Account("andrey"), new Account("angela"), new Account("pamela")));

        Stream<Account> s = accounts.stream();

        s
                .filter(a -> propagate(a::isActive))
                .map(a -> a.name)
                .forEach(System.out::println);
    }
}
