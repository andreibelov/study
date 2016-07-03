package ru.andrw.java.jsonchat.dao;

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import static org.junit.Assert.assertTrue;

/** 
* ChatDAO Tester. 
* 
* @author <Authors name> 
* @since <pre>Jul 3, 2016</pre> 
* @version 1.0 
*/ 
public class ChatDAOTest {
    private DAO dao;

    @Before
    public void before() throws Exception, DAOException {
        String path = "/Chat.xml";
        try {
            dao = (DAO) JAXBContext.newInstance(ChatDAO.class)
                    .createUnmarshaller()
                    .unmarshal(ChatDAO.class.getResourceAsStream(path));
        } catch (JAXBException e) {
            throw  new DAOException();
        }
    }

    @After
    public void after() throws Exception {
    }

    /**
    *
    * Method: getUser(Long id)
    *
    */
    @Test
    public void testGetUser() throws Exception, DAOException {
        assertTrue(dao.getUser(-1l) == null);
        assertTrue(dao.getUser(1l) == null);
        assertTrue(dao.getUser(0l) != null);
    }

    /**
    *
    * Method: findUser(String login, String pass)
    *
    */
    @Test
    public void testFindUser() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: addUser(User user)
    *
    */
    @Test
    public void testAddUser() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: deleteUser(User user)
    *
    */
    @Test
    public void testDeleteUser() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: changePassword(User user)
    *
    */
    @Test
    public void testChangePassword() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: getMessage(Long id)
    *
    */
    @Test
    public void testGetMessage() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: findMessage(String text)
    *
    */
    @Test
    public void testFindMessage() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: addMessage(Message message)
    *
    */
    @Test
    public void testAddMessage() throws Exception {
    //TODO: Test goes here...
    }

    /**
    *
    * Method: deleteMessage(Message message)
    *
    */
    @Test
    public void testDeleteMessage() throws Exception {
    //TODO: Test goes here...
    }


} 
