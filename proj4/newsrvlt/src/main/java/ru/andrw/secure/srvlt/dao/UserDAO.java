package ru.andrw.secure.srvlt.dao;

import ru.andrw.secure.srvlt.model.User;

/**
 * Created by john on 6/24/2016.
 *
 */

public interface UserDAO {
    public User find(Long id) throws DAOException;
    public User find(String username, String password) throws DAOException;
}