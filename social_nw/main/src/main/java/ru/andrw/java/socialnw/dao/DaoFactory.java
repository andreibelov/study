package ru.andrw.java.socialnw.dao;

/**
 * Created by john on 9/25/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public interface DaoFactory {
    public UserDao getUserDao();
    public UserProfileDao getProfileDao();
}
