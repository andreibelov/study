package com.epam.courses.junit;

import java.util.Iterator;

/**
 * Created by john on 11/20/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class ArithmeticException extends RuntimeException implements Iterable<Throwable> {

    public ArithmeticException() {
        super();
    }
    public ArithmeticException(String message) {
        super(message);
    }
    public ArithmeticException(String message, Throwable cause) {
        super(message, cause);
    }
    public ArithmeticException(Throwable cause) {
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
