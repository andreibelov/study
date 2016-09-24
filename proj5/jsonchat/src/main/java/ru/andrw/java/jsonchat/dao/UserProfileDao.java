package ru.andrw.java.jsonchat.dao;

import ru.andrw.java.jsonchat.model.UserProfile;

import java.util.List;

/**
 * Created by john on 9/23/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public interface UserProfileDao {

    public List<UserProfile> getAllUserProfiles();
    public List<UserProfile> searchUserProfilesByName(String name);
    public UserProfile getUserProfile(Long id) throws DaoException;
    public long addUserProfile(UserProfile profile) throws DaoException;
    public boolean updateUserProfile(UserProfile profile);
    public boolean deleteUserProfile(Long id);
}
