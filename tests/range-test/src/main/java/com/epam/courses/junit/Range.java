package com.epam.courses.junit;

import java.util.Iterator;
import java.util.List;

/**
 * Created by john on 11/21/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public interface Range {
    boolean isBefore(Range otherRange);
    boolean isAfter(Range otherRange);
    boolean isConcurrent(Range otherRange);
    long getLowerBound();
    long getUpperBound();
    boolean contains(long value);
    List<Long> asList();
    Iterator<Long> asIterator();
}
