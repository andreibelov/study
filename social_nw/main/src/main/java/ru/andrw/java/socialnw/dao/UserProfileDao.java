package ru.andrw.java.socialnw.dao;

import ru.andrw.java.socialnw.model.UserProfile;

import java.util.List;

/**
 * Created by john on 9/25/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public interface UserProfileDao {
    public List<UserProfile> getUserProfilesSubList(Integer offset, Integer limit) throws DaoException;
    public List<UserProfile> searchUserProfilesByName(String name);
    public UserProfile searchUserProfileByEmail(String email) throws DaoException;
    public UserProfile getUserProfileById(Long id) throws DaoException;
    public Long addUserProfile(UserProfile profile) throws DaoException;
    public void updateUserProfile(UserProfile profile) throws DaoException;
    public boolean deleteUserProfile(Long id);
}