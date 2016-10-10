package ru.andrw.java.socialnw.dao;

import ru.andrw.java.socialnw.model.UserProfile;

import java.util.List;
import java.util.Optional;

/**
 * Created by john on 9/25/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public interface UserProfileDao {
    public List<UserProfile> getUserProfilesSubList(Integer offset, Integer limit) throws DaoException;
    public List<UserProfile> searchUserProfilesByName(String name);
    public Optional<UserProfile> searchUserProfileByEmail(String email);
    public Optional<UserProfile> getUserProfileById(Long id);
    public UserProfile addUserProfile(UserProfile profile) throws DaoException;
    public void updateUserProfile(UserProfile profile) throws DaoException;
    public boolean deleteUserProfile(Long id);
}