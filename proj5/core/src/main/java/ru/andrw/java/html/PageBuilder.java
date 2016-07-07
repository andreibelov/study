package ru.andrw.java.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by john on 7/7/2016.
 */
public class PageBuilder {

    public void execute() throws IOException {
        Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
        Element body = doc.body();
        System.out.println(body);
    }
}
