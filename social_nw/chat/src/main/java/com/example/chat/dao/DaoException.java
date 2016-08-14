package com.example.chat.dao;

import java.util.Iterator;

/**
 * Created by john on 8/15/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class DaoException extends Exception implements Iterable<Throwable> {
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
