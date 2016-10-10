package com.example.chat.dao;

/**
 * Created by john on 8/14/2016.
 * Some type of IoC (inversion of control) implementation
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public interface DaoFactory {
    public UserDao getUserDao();
    public ChatMessageDao getChatMessageDao();
    public UserProfileDao getProfileDao();
}
