package com.epam.courses.junit;

import java.io.IOException;
import java.util.Properties;
import java.util.stream.IntStream;

/**
 * Created by john on 11/15/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class MyMath {

    private static long factorial(int i){
        return IntStream.rangeClosed(1,i)
                .mapToLong(u->(long) u)
                .reduce(1L, (ir, v) -> ir*v);
    }

    static long calculate(Number n)throws ArithmeticException{
        if (n.longValue() > Integer.MAX_VALUE )
            throw new ArithmeticException("input is over Integer.MAX_VALUE!");
        return factorial(n.intValue());
    }

}
