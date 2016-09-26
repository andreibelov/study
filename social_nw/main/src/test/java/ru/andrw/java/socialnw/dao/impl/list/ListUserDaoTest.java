package ru.andrw.java.socialnw.dao.impl.list;

import org.apache.tomcat.util.security.ConcurrentMessageDigest;
import org.apache.tomcat.util.security.MD5Encoder;
import org.junit.Before;
import org.junit.Test;
import ru.andrw.java.socialnw.dao.UserDao;
import ru.andrw.java.socialnw.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * ru.andrw.java.socialnw.dao.impl.list.ListUserDao Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre></pre>
 */
public class ListUserDaoTest {

    private UserDao userDao;

    @Before
    public void setUp() throws Exception {

        AtomicLong al = new AtomicLong(0L);
        List<User> userList = new CopyOnWriteArrayList<>();

        String pass = MD5Encoder.encode(ConcurrentMessageDigest.digestMD5("admin".getBytes()));

        userList.add((new User()).setId(al.get())
                .setEmail("andrei.belov@mail.ru")
                .setLogin("andrei.belov")
                .setPassword(pass));

        this.userDao = new ListUserDao(userList,al);
    }

    /**
     * Method: getUserById
     */
    @Test
    public void getUserById() throws Exception {
        Optional<User> user = Optional.empty();
        user = userDao.getUserById(0L);
        assertThat(user.isPresent(), is(true));
        if(user.isPresent())
            System.out.println(user.get());
    }

    /**
     * Method: findUser
     */
    @Test
    public void findUser() throws Exception {

        Optional<User> user = Optional.empty();
        user = userDao.findUser("andrei.belov@mail.ru",
                MD5Encoder.encode(ConcurrentMessageDigest
                        .digestMD5("admin".getBytes())));
        assertThat(user.isPresent(), is(true));
        if(user.isPresent())
        System.out.println(user.get());
    }

    /**
     * Method: addUser
     */
    @Test
    public void addUser() throws Exception {
        //TODO: Test goes here...

    }

    /**
     * Method: deleteUser
     */
    @Test
    public void deleteUser() throws Exception {
        //TODO: Test goes here...

    }

    /**
     * Method: updateUser
     */
    @Test
    public void updateUser() throws Exception {
        //TODO: Test goes here...

    }

    /**
     * Method: getUsersSubList
     */
    @Test
    public void getUsersSubList() throws Exception {
        //TODO: Test goes here...

    }

}