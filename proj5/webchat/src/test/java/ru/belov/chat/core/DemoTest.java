package ru.belov.chat.core;

import org.junit.Before;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static ru.belov.chat.core.Demo.matcherStream;

/**
 * ru.belov.chat.core.Demo Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre></pre>
 */
public class DemoTest {
    @Before
    public void setUp() throws Exception {

    }

    /**
     * Method: matcherStream
     */
    @Test
    public void matcherStreamTest() throws Exception {
        Matcher matcher = Pattern.compile("^\\/(\\p{L}{1,32}\\.(?i)jsp)\\???")
                .matcher("/LOGIN.jSp?pAsS=123456");
        matcherStream(matcher)
                .map(gs->gs[1].toLowerCase())
                .findAny();
    }

    /**
     * Method: doSmth
     */
    @Test
    public void doSmth() throws Exception {
        //TODO: Test goes here...

    }

}