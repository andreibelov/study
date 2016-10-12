package ru.andrw.java.socialnw.dao;

import java.util.Optional;

import ru.andrw.java.socialnw.model.auth.User;

/**
 * Created by john on 10/9/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public interface TokensDao {
    public void addToken(User user);
    public void removeToken(String key);
    public Optional<Long> findUserId(String key);
}
