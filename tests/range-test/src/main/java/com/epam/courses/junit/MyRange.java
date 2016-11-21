package com.epam.courses.junit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Created by john on 11/21/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class MyRange implements Range {

    private final long lowerBound;
    private final long upperBound;

    MyRange(final long lowerBound, final long upperBound){
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public static Range getRandom(long m, long n){
        final long upperBound = ThreadLocalRandom.current().nextLong(m, n);
        final long lowerBound = ThreadLocalRandom.current().nextLong(m, upperBound);
        return new MyRange(lowerBound,upperBound);
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
        return lowerBound;
    }

    @Override
    public long getUpperBound() {
        return upperBound;
    }

    @Override
    public boolean contains(long value) {
        return (lowerBound<=value)&&(upperBound>=value);
    }

    @Override
    public List<Long> asList() {
        return LongStream.rangeClosed(lowerBound,upperBound)
                .mapToObj(Long::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public Iterator<Long> asIterator() {
        return LongStream.rangeClosed(lowerBound,upperBound)
                .mapToObj(Long::valueOf)
                .collect(Collectors.toList()).iterator();
    }
}
