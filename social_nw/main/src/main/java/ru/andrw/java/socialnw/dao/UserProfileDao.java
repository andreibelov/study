package ru.andrw.java.socialnw.dao;

import ru.andrw.java.socialnw.model.Profile;

import java.util.List;
import java.util.Optional;

/**
 * Created by john on 9/25/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public interface UserProfileDao {
    public List<Profile> getUserProfilesSubList(Integer offset, Integer limit) throws DaoException;
    public List<Profile> searchUserProfilesByName(String name);
    public Optional<Profile> searchUserProfileByEmail(String email);
    public Optional<Profile> getUserProfileById(Long id);

    Profile regNewProfile(Profile profile) throws DaoException;

    public Profile addUserProfile(Profile profile) throws DaoException;
    public void updateUserProfile(Profile profile) throws DaoException;
    public void deleteUserProfile(Long id) throws DaoException;
}