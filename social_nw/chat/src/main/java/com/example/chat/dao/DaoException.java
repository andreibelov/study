package com.example.chat.dao;

import java.util.Iterator;

/**
 * Created by john on 8/15/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class DaoException extends Exception implements Iterable<Throwable> {

    public DaoException() {
        super();
    }
    public DaoException(String message) {
        super(message);
    }
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
    public DaoException(Throwable cause) {
        super(cause);
    }

    @Override
    public Iterator<Throwable> iterator() {
        return new Iterator<Throwable>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Throwable next() {
                return null;
            }
        };
    }
}