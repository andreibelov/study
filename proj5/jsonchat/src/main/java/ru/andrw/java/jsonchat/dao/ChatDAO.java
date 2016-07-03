package ru.andrw.java.jsonchat.dao;

import ru.andrw.java.jsonchat.model.ChatMessage;
import ru.andrw.java.jsonchat.model.User;
import ru.andrw.java.jsonchat.servlet.Chat;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

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
       return users.stream().filter(user -> id.equals(user.getId()))
               .findAny().orElse(null);
    }

    @Override
    public User findUser(String login, String pass) throws DAOException {
        return users.stream().filter(user -> login.equals(user.getLogin()))
                .findAny().filter(user -> pass.equals(user.getPassword()))
                .orElse(null);
    }

    @Override
    public boolean addUser(User user) {
        if(!userValidator(user)) return false;
        else users.add(user);
        return true;
    }

    private boolean userValidator(User user){
        return ofNullable(user).filter(u -> u != null)
                .filter(u -> u.getLogin() != null)
                .filter(u -> u.getPassword() != null)
                .filter(u -> u.getEmail() != null)
                .isPresent();
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
    public long addMessage(ChatMessage message) {

        return 0;
    }

    @Override
    public boolean deleteMessage(ChatMessage message) {
        return false;
    }
}
