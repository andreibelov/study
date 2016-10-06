package ru.andrw.java.socialnw.wrapper;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/**
 * Created by john on 10/6/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */

public class FilterServletOutputStream extends ServletOutputStream {

    private DataOutputStream stream;

    public FilterServletOutputStream(OutputStream output) {
        stream = new DataOutputStream(output);
    }

    public void write(int b) throws IOException  {
        stream.write(b);
    }

    public void write(byte[] b) throws IOException  {
        stream.write(b);
    }

    public void write(byte[] b, int off, int len) throws IOException  {
        stream.write(b,off,len);
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener listener) {

    }
}
