package ru.belov.chat.core;

import java.util.regex.Matcher;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by john on 7/7/2016.
 */
public class Demo {

    public static Stream<String[]> matcherStream(Matcher m) {
        return StreamSupport.stream(new MatchItr(m), false);
    }

    public void doSmth(){
        System.out.println("Hey-Hey!");
    }
}
