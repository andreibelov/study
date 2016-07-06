package test.ru.andrw.java.multithreading.workers;

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import ru.andrw.java.multithreading.workers.Manager;

/** 
* Manager Tester. 
* 
* @author <Authors name> 
* @since <pre>Jul 6, 2016</pre> 
* @version 1.0 
*/ 
public class ManagerTest { 

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
        Manager manager = new Manager();
        manager.doWork();
    }

}
