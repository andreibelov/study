package ru.andrw.java.jsonchat.dao;

import ru.andrw.java.jsonchat.model.ChatMessage;
import ru.andrw.java.jsonchat.model.User;
import sun.plugin2.message.Message;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by john on 7/3/2016.
 */
@XmlRootElement(name="root")
@XmlAccessorType(XmlAccessType.FIELD)
public class ChatDAO implements DAO {
    @XmlElementWrapper(name="users")
    @XmlElement(name="user")
    private List<User> users;
    @XmlElementWrapper(name="messages")
    @XmlElement(name="message")
    private List<ChatMessage> messages;


    @Override
    public User getUser(Long id) throws DAOException {
        return null;
    }

    @Override
    public User findUser(String login, String pass) throws DAOException {
        return null;
    }

    @Override
    public boolean addUser(User user) {
        return false;
    }

    @Override
    public boolean deleteUser(User user) {
        return false;
    }

    @Override
    public void changePassword(User user) throws DAOException {

    }

    @Override
    public ChatMessage getMessage(Long id) throws DAOException {
        return null;
    }

    @Override
    public ChatMessage findMessage(String text) throws DAOException {
        return null;
    }

    @Override
    public long addMessage(Message message) {
        return 0;
    }

    @Override
    public boolean deleteMessage(Message message) {
        return false;
    }
}
