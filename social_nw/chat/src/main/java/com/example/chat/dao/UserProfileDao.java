package com.example.chat.dao;

import com.example.chat.model.UserProfile;

import java.util.List;

public interface UserProfileDao {

    public List<UserProfile> getAllUserProfiles();
    public List<UserProfile> searchUserProfilesByName(String name);
    public UserProfile getUserProfile(Long id) throws DaoException;
    public long addUserProfile(UserProfile profile) throws DaoException;
    public boolean updateUserProfile(UserProfile profile);
    public boolean deleteUserProfile(Long id);
}
