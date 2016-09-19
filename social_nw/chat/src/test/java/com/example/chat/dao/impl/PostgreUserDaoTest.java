package com.example.chat.dao.impl;

import com.example.chat.dao.UserDao;
import com.example.chat.model.User;
import com.example.chat.util.DbUtil;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.Optional;
import java.util.function.Supplier;

import static org.junit.Assert.*;

/**
 * com.example.chat.dao.impl.PostgreUserDao Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre></pre>
 */
public class PostgreUserDaoTest {

    private Supplier<Connection> connectionSupplier;
    private UserDao userDao;

    @Before
    public void setUp() throws Exception {
        this.connectionSupplier = new DbUtil();
        this.userDao = new PostgreUserDao(connectionSupplier);
    }

    /**
     * Method: find
     */
    @Test
    public void find() throws Exception {

        String login = "andrei.belov@mail.ru";
        String pass = "password";

        Optional<User> user = userDao.find(login,pass);

        user.ifPresent(System.out::println);

    }

    /**
     * Method: getAllUsers
     */
    @Test
    public void getAllUsers() throws Exception {
        //TODO: Test goes here...

    }

    /**
     * Method: getUserById
     */
    @Test
    public void getUserById() throws Exception {
        //TODO: Test goes here...

    }

}