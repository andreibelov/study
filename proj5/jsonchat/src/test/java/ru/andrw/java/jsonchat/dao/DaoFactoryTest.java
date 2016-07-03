package ru.andrw.java.jsonchat.dao;

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import ru.andrw.java.jsonchat.model.User;

/** 
* DaoFactory Tester. 
* 
* @author <Authors name> 
* @since <pre>Jul 3, 2016</pre> 
* @version 1.0 
*/ 
public class DaoFactoryTest { 

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: getDao()
    *
    */
    @Test
    public void testGetDao() throws Exception, DAOException {
        DAO chat = DaoFactory.getDao();
        User user = chat.findUser("white.rabbit", "b962ac59075b964b07152d234b70");
        System.out.println(user);
    }

    /**
    *
    * Method: saveDao()
    *
    */
    @Test
    public void testSaveDao() throws Exception {
    //TODO: Test goes here...
    }


} 
