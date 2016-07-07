package ru.belov.chat.control;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
/**
 * Created by john on 7/7/2016.
 */
public class PageBuilder {
    public String getPage() throws IOException {
        Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
        Element body = doc.body();
        return body.html();
    }
}
