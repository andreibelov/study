package com.epam.courses.junit;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * com.epam.courses.junit.MyMath Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre></pre>
 */
@RunWith(Parameterized.class)
public class MyMathTest {

    private Number inputNumber;
    private Long expectedResult;

    public MyMathTest(Number inputNumber, Long expectedResult) {
        this.inputNumber = inputNumber;
        this.expectedResult = expectedResult;
    }


    @Parameterized.Parameters
    public static Collection primeNumbers() {
        Object[][] array =  {
                { 5, 120L },
                { 6, 720L },
                { Integer.MAX_VALUE+1L, 1L },
        };
        return Arrays.asList(array);
    }

    /**
     * Method: calculate();
     */
    @Test
    public void testCalculation(){
        Assume.assumeTrue(inputNumber.longValue()<=Integer.MAX_VALUE);
        assertThat(MyMath.calculate(inputNumber),is(expectedResult));
    }


    /**
     * Method: calculate(); with wrong input.
     */
    @Test(expected = ArithmeticException.class)
    public void testException() throws Exception {
        Assume.assumeTrue(inputNumber.longValue()>Integer.MAX_VALUE);
        assertThat(MyMath.calculate(inputNumber),is(expectedResult));
    }



}