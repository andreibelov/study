package ru.andrw.secure.srvlt.dao;

import ru.andrw.secure.srvlt.model.User;

/**
 * Created by john on 6/24/2016.
 *
 */

public class UserDAOProps implements UserDAO {
/*
    DAOProperties properties = new DAOProperties(name);
    String url = properties.getProperty(PROPERTY_URL, true);
    String driverClassName = properties.getProperty(PROPERTY_DRIVER, false);
    String password = properties.getProperty(PROPERTY_PASSWORD, false);
    String username = properties.getProperty(PROPERTY_USERNAME, password != null);
    DAOFactory instance;
*/
    // Actions ------------------------------------------------------------------------------------

    @Override
    public User find(Long id) throws DAOException {
        return find(id);
    }

    @Override
    public User find(String email, String password) throws DAOException {
        return find(email, password);
    }

}
