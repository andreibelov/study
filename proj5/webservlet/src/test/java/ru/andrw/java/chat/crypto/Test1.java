package ru.andrw.java.chat.crypto;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by john on 7/1/2016.
 * Trying to test encryption
 */
public class Test1 {

    @Test
    public void main() throws Exception {
        String password = "123";
        assertThat(MD5Encrypter.encrypt(password), is("202cb962ac59075b964b07152d234b70"));
    }

}
