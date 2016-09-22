package ru.andrw.java.jsonchat.dao;

import java.util.Iterator;

/**
 * Created by john on 7/3/2016.
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