package ru.andrw.java.jsonchat.dao;

import ru.andrw.java.jsonchat.model.chat.ChatMessage;
import ru.andrw.java.jsonchat.model.User;

/**
 * Created by john on 7/3/2016.
 *
 */
public interface DAO {

    public User getUser(Long id) throws DAOException;
    public User findUser(String login, String pass) throws DAOException;
    public boolean addUser(User user);
    public boolean deleteUser(User user);
    public void changePassword(User user) throws DAOException;

    public ChatMessage getMessage(Long id) throws DAOException;
    public ChatMessage findMessage(String text) throws DAOException;
    public long addMessage(ChatMessage message);
    public boolean deleteMessage(ChatMessage message);

}
