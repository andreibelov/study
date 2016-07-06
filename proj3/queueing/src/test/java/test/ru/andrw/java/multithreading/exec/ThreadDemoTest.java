package test.ru.andrw.java.multithreading.exec;

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import ru.andrw.java.multithreading.exec.ThreadDemo;

/** 
* ThreadDemo Tester. 
* 
* @author <Authors name> 
* @since <pre>Jul 7, 2016</pre> 
* @version 1.0 
*/ 
public class ThreadDemoTest { 

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: doWork()
    *
    */
    @Test
    public void testDoWork() throws Exception {
        (new ThreadDemo()).doWork();
    }


} 
