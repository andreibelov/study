package ru.andrw.java.socialnw.dao.impl.h2;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

/**
 * ru.andrw.java.socialnw.dao.impl.h2.H2IMDao Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre></pre>
 */
public class H2IMDaoTest {

    @Before
    public void setUp() throws Exception {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("samplePU");
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction et = em.getTransaction();
    }

    /**
     * Method: newChatRoom
     */
    @Test
    public void newChatRoom() throws Exception {
        //TODO: Test goes here...

    }

    /**
     * Method: addNewIM
     */
    @Test
    public void addNewIM() throws Exception {
        //TODO: Test goes here...

    }

    /**
     * Method: getConnection
     */
    @Test
    public void getConnection() throws Exception {
        //TODO: Test goes here...

    }

}