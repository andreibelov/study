package ru.andrw.java.socialnw.dao.impl.h2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import ru.andrw.java.socialnw.dao.UserDao;
import ru.andrw.java.socialnw.model.auth.User;
import ru.andrw.java.socialnw.pooling.ConnectionPool;

import static org.junit.Assert.*;

/**
 * ru.andrw.java.socialnw.dao.impl.h2.H2UserDao Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre></pre>
 */
@SuppressWarnings("FieldCanBeLocal")
public class H2UserDaoTest {
    private static ConnectionPool connectionPool;

    private static final String DB_PROPERTIES_FILE_NAME = "h2.db.properties";
    private UserDao userDao;
    private String email = "nyaka@email.com";
    private String login = "nyaka.nyaka";
    private String pass = "encoded_string";
    private String new_pass = "new_encoded_string";
    private User user;

    @Before
    public void setUp() throws Exception {

        URL dbPropertiesURL = H2UserDao.class.getClassLoader()
                .getResource(DB_PROPERTIES_FILE_NAME);

        final String dbPropertiesFilePath = ( dbPropertiesURL != null) ?
                dbPropertiesURL.getPath() : "";
        userDao = (new H2DaoFactory(ConnectionPool.create(dbPropertiesFilePath)).getUserDao());

    }

    /**
     * Method: addUser
     */
    @Test
    public void addUser() throws Exception {

        user = userDao.addUser((new User())
                .setEmail(email)
                .setLogin(login)
                .setPassword(pass));
        assertTrue(user.getId() != null);
    }

    /**
     * Method: getUserByLogin
     */
    @Test
    public void getUserByLogin() throws Exception {
        Optional<User> userOptional = userDao.getUserByLogin(login);
        assertTrue(userOptional.isPresent());
    }

    /**
     * Method: findUser
     */
    @Test
    public void findUser() throws Exception {

        Optional<User> o_user = userDao.findUser(email,pass);
        assertTrue(o_user.isPresent());

    }

    /**
     * Method: getUserById
     */
    @Test
    public void getUserById() throws Exception {

        Optional<User> o_user = userDao.getUserByLogin(login);
        if(o_user.isPresent()) o_user = userDao.getUserById(o_user.get().getId());
        assertTrue(o_user.isPresent());

    }

    /**
     * Method: getUserByEmail
     */
    @Test
    public void getUserByEmail() throws Exception {
        Optional<User> o_user = userDao.getUserByLogin(login);
        if(o_user.isPresent()) o_user = userDao.getUserByEmail(o_user.get().getEmail());
        assertTrue(o_user.isPresent());

    }

    /**
     * Method: changePassword
     */
    @Test
    public void changePassword() throws Exception {
        Optional<User> o_user = userDao.getUserByLogin(login);
        if(o_user.isPresent()) userDao.changePassword(o_user.get().getId(),new_pass);
        o_user = userDao.findUser(email,new_pass);
        assertTrue(o_user.isPresent());

    }

    /**
     * Method: getUsersSubList
     */
    @Test
    public void getUsersSubList() throws Exception {

        List<User> userList = userDao.getUsersSubList(0,30);
        assertTrue(!userList.isEmpty());
        for (User u :
                userList) {
            System.out.println(u);
        }

    }

    /**
     * Method: updateUser
     */
    @Test
    public void updateUser() throws Exception {
        Optional<User> o_user = userDao.getUserByLogin(login);
        if(o_user.isPresent()) user = o_user.get();
        userDao.updateUser(user.setAccessLevel(0));
        o_user = userDao.getUserById(user.getId());
        assertTrue(o_user.isPresent());
        assertTrue(o_user.get().getAccessLevel() == 0);
    }

    /**
     * Method: deleteUser
     */
    //@Test
    public void deleteUser() throws Exception {
        Optional<User> o_user = userDao.getUserByLogin(login);
        if(o_user.isPresent()) {
            userDao.deleteUser(o_user.get().getId());
            assertTrue(!userDao.getUserById(o_user.get().getId()).isPresent());
        }
    }

    /**
     * Method: getConnection
     */
    @Test
    public void getConnection() throws Exception {



    }

    @After
    public void tearDown() throws Exception {

    }
}