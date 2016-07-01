package ru.andrw.java.sprg;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Created by john on 6/28/2016.
 *
 */

public class MyTest {

    @Test
    public void methodTest() throws Exception {
        assertThat(MyClass.myMethod(), is(13));
    }

}
