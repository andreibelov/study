package ru.andrw.java.socialnw.dao.impl.h2;

import java.sql.Connection;
import java.util.function.Supplier;

import com.epam.courses.jf.dao.Dao;
import ru.andrw.java.socialnw.dao.DaoFactory;
import ru.andrw.java.socialnw.dao.TokensDao;
import ru.andrw.java.socialnw.dao.UserDao;
import ru.andrw.java.socialnw.dao.UserProfileDao;

/**
 * Created by john on 10/10/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class H2DaoFactory implements DaoFactory, Dao {

    private final Supplier<Connection> connectionSupplier;

    private UserDao userDao;
    private TokensDao tokensDao;
    private UserProfileDao profileDao;

    public H2DaoFactory(Supplier<Connection> supplier){
        this.connectionSupplier = supplier;
        this.profileDao = new H2UserProfileDao(connectionSupplier);
        this.userDao = new H2UserDao(connectionSupplier);
        this.tokensDao = new H2TokensDao(connectionSupplier);
    }

    @Override
    public UserDao getUserDao() {
        return userDao;
    }

    @Override
    public UserProfileDao getProfileDao() {
        return profileDao;
    }

    @Override
    public TokensDao getTokensDao() {
        return tokensDao;
    }

    @Override
    public Connection getConnection() {
        return connectionSupplier.get();
    }
}
