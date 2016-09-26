package ru.andrw.java.socialnw.dao;

import ru.andrw.java.socialnw.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by john on 9/25/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public interface UserDao {

    /**
     * Returns the user from the database matching the given email and password, otherwise null.
     * @param login The login of the user to be returned.
     * @param password The password of the user to be returned.
     * @return The user from the database matching the given email and password, otherwise null.
     * @throws DaoException If something fails at database level.
     */
    Optional<User> findUser(String login, String password) throws DaoException;

    public void addUser(User user) throws DaoException;
    public void deleteUser(Long userId) throws DaoException;
    public void updateUser(User user) throws DaoException;
    public List<User> getUsersSubList(Long skipFirst, Long limitMax) throws DaoException;
    public Optional<User> getUserById(Long userId) throws DaoException;
}

