package ru.andrw.java.socialnw.dao.impl.h2;

import java.sql.Connection;
import java.util.function.Supplier;

import com.epam.courses.jf.dao.Dao;
import ru.andrw.java.socialnw.dao.DaoFactory;
import ru.andrw.java.socialnw.dao.FriendsDao;
import ru.andrw.java.socialnw.dao.IMDao;
import ru.andrw.java.socialnw.dao.PostDao;
import ru.andrw.java.socialnw.dao.SectionDao;
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
    private IMDao imDao;
    private TokensDao tokensDao;
    private FriendsDao friendsDao;
    private UserProfileDao profileDao;
    private SectionDao sectionDao;
    private PostDao postDao;

    public H2DaoFactory(Supplier<Connection> supplier){

        this.connectionSupplier = supplier;
        this.profileDao = new H2UserProfileDao(connectionSupplier);
        this.userDao = new H2UserDao(connectionSupplier);
        this.imDao = new H2IMDao(connectionSupplier);
        this.tokensDao = new H2TokensDao(connectionSupplier);
        this.friendsDao = new H2FriendsDao(connectionSupplier);
        this.sectionDao = new H2SectionDao(connectionSupplier);
        this.postDao = new H2PostDao(connectionSupplier);


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
    public FriendsDao getFriendsDao() {
        return friendsDao;
    }

    @Override
    public IMDao getIMDao() {
        return imDao;
    }

    @Override
    public PostDao getPostDao() {
        return postDao;
    }

    @Override
    public SectionDao getSectionDao() {
        return sectionDao;
    }

    @Override
    public Connection getConnection() {
        return connectionSupplier.get();
    }
}
