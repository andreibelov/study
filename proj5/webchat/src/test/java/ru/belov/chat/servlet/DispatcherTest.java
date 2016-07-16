package ru.belov.chat.servlet;

import org.junit.Before;
import org.junit.Test;
import ru.belov.chat.core.MatchItr;

import java.net.URI;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.StreamSupport.stream;
import static org.junit.Assert.*;

/**
 * ru.belov.chat.servlet.Dispatcher Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre></pre>
 */
public class DispatcherTest {
    @Before
    public void setUp() throws Exception {

    }

    /**
     * Method: doDispatch
     */
    @Test
    public void doDispatch() throws Exception {
        String uri = URI.create("http://localhost:8080/webchat/chat.jsp?dadiwjadi").toString();
        String context = "http://localhost:8080/webchat";
        Matcher matcher = Pattern.compile("^/(\\p{L}{1,32}\\.jsp)\\???.*")
                .matcher(uri.substring(context.length()));

        String to = stream(new MatchItr(matcher), false)
                .map(gs->gs[1].toLowerCase())
                .findAny().orElse("main.jsp");

        System.out.println("jsp\\"+to);

    }

    /**
     * Method: doGet
     */
    @Test
    public void doGet() throws Exception {
        //TODO: Test goes here...

    }

}