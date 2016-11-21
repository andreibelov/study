package com.epam.courses.junit;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiPredicate;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * com.epam.courses.junit.Range Tester.
 * @author <Authors name>
 * @version 1.0
 * @since <pre></pre>
 */
public class RangeTest {

    private static final long m = -1000L;
    private static final long n = 1000L;

    private Range range;
    private Range otherRange;

    @Before
    public void setUp() throws Exception {
        range = MyRange.getRandom(m,n);
        otherRange = MyRange.getRandom(m,n);
    }

    /**
     * Methods tested: isBefore, isAfter, isConcurrent
     */
    @Test
    public void comparisionCheck() throws Exception {

        Map<BiPredicate<Range,Range>,String> comparisionMap = new HashMap<>();
        comparisionMap.put(Range::isConcurrent,"isConCurrent");
        comparisionMap.put(Range::isBefore,"isBefore");
        comparisionMap.put(Range::isAfter,"isAfter");

        Optional<String> stringOptional =
                comparisionMap.entrySet().stream()
                        .filter(e->e.getKey().test(range,otherRange))
                        .map(Map.Entry::getValue).findAny();

        assertTrue(stringOptional.isPresent());
        System.out.println(stringOptional.get());
    }

    /**
     * Methods tested: getUpperBound, getLowerBound.
     */
    @Test
    public void upperBoundIsGraterThanOrEqualsToLowerBound() throws Exception {
        assertTrue(range.getUpperBound()>=range.getLowerBound());
    }

    /**
     * Method: contains
     */
    @Test
    public void contains() throws Exception {
        final long randomLongInRange = ThreadLocalRandom.current().nextLong(range.getLowerBound(), range.getUpperBound()+1);
        assertTrue(range.contains(randomLongInRange));
    }

    /**
     * Method: asList
     */
    @Test
    public void asList() throws Exception {
        int expected = Long.valueOf(range.getUpperBound()-range.getLowerBound()+1L).intValue();
        assertThat(range.asList().size(), is(expected));
    }

    /**
     * Method: asIterator
     */
    @Test
    public void asIterator() throws Exception {
        assertTrue(range.asIterator() != null);
    }

}