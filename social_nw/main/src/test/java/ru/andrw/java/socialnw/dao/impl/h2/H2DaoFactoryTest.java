package ru.andrw.java.socialnw.dao.impl.h2;

import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import ru.andrw.java.socialnw.dao.TokensDao;
import ru.andrw.java.socialnw.dao.UserDao;
import ru.andrw.java.socialnw.dao.UserProfileDao;
import ru.andrw.java.socialnw.model.auth.User;
import ru.andrw.java.socialnw.pooling.ConnectionPool;
import ru.andrw.java.socialnw.model.Profile;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * ru.andrw.java.socialnw.dao.impl.h2.H2DaoFactory Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre></pre>
 */
@SuppressWarnings("FieldCanBeLocal")
public class H2DaoFactoryTest {

    private static final String DB_PROPERTIES_FILE_NAME = "h2.db.properties";
    private static final String DB_PREPARE_FILE_NAME = "h2.sql";

    private static ConnectionPool connectionPool;
    private H2DaoFactory daoFactory;
    private UserDao userDao;
    private TokensDao tokensDao;
    private H2FriendsDao friendsDao;
    private UserProfileDao profileDao;

    @Before
    public void setUp() throws Exception {

        URL dbPropertiesURL = H2UserDao.class.getClassLoader()
                .getResource(DB_PROPERTIES_FILE_NAME);

        final String dbPropertiesFilePath = ( dbPropertiesURL != null) ?
                dbPropertiesURL.getPath() : "";
        connectionPool = ConnectionPool.create(dbPropertiesFilePath);
        daoFactory = new H2DaoFactory(connectionPool);
    }

    /**
     * Method: getUserDao
     */
    @Test
    public void getUserDao() throws Exception {

        assertTrue(daoFactory.getUserDao() != null);

    }

    /**
     * Method: getProfileDao
     */
    @Test
    public void getProfileDao() throws Exception {
        assertTrue(daoFactory.getProfileDao() != null);
    }

    /**
     * Method: getTokensDao
     */
    @Test
    public void getTokensDao() throws Exception {
        assertTrue(daoFactory.getTokensDao() != null);

    }

    /**
     * Method: getConnection
     */
    @Test
    public void getConnection() throws Exception {
        Connection connection = daoFactory.getConnection();
        assertTrue(connection != null);
        assertThat(connection.isValid(10), is(true));
    }

}