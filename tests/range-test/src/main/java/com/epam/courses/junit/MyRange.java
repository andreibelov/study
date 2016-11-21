package com.epam.courses.junit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by john on 11/21/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class MyRange implements Range {

    private long[] array;

    MyRange(long[] array){
        this.array = array;
    }

    @Override
    public boolean isBefore(Range otherRange) {
        return this.getUpperBound()<otherRange.getLowerBound();
    }

    @Override
    public boolean isAfter(Range otherRange) {
        return this.getLowerBound()>otherRange.getUpperBound();
    }

    @Override
    public boolean isConcurrent(Range otherRange) {
        return (this.getUpperBound() >= otherRange.getLowerBound())||
                (otherRange.getUpperBound() >= this.getLowerBound());
    }

    @Override
    public long getLowerBound() {
        return array[0];
    }

    @Override
    public long getUpperBound() {
        return array[array.length-1];
    }

    @Override
    public boolean contains(long value) {
        return Arrays.stream(array)
                .filter(l->value==l).findAny().isPresent();
    }

    @Override
    public List<Long> asList() {
        return Arrays.stream(array).mapToObj(Long::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public Iterator<Long> asIterator() {
        return Arrays.stream(array).mapToObj(Long::valueOf)
                .collect(Collectors.toList()).iterator();
    }
}
