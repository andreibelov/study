package ru.andrw.java.socialnw.dao.impl.h2;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import com.epam.courses.jf.dao.Dao;
import ru.andrw.java.socialnw.dao.DaoException;
import ru.andrw.java.socialnw.dao.UserProfileDao;
import ru.andrw.java.socialnw.model.Profile;

/**
 * Created by john on 10/11/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
class H2UserProfileDao implements UserProfileDao, Dao {

    private final Supplier<Connection> supplier;

    H2UserProfileDao(Supplier<Connection> supplier){
        this.supplier = supplier;
    }

    @Override
    public List<Profile> getUserProfilesSubList(Integer offset, Integer limit) throws DaoException {
        return null;
    }

    @Override
    public List<Profile> searchUserProfilesByName(String name) {
        return null;
    }

    @Override
    public Optional<Profile> searchUserProfileByEmail(String email) {
        return null;
    }

    @Override
    public Optional<Profile> getUserProfileById(Long id) {
        return null;
    }

    @Override
    public Profile addUserProfile(Profile profile) throws DaoException {
        return null;
    }

    @Override
    public void updateUserProfile(Profile profile) throws DaoException {

    }

    @Override
    public boolean deleteUserProfile(Long id) {
        return false;
    }

    @Override
    public Connection getConnection() {
        return supplier.get();
    }
}
