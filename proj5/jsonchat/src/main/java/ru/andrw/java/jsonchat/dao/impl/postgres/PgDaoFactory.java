package ru.andrw.java.jsonchat.dao.impl.postgres;

import ru.andrw.java.jsonchat.dao.ChatMessageDao;
import ru.andrw.java.jsonchat.dao.DaoFactory;
import ru.andrw.java.jsonchat.dao.UserDao;
import ru.andrw.java.jsonchat.dao.UserProfileDao;

import java.sql.Connection;
import java.util.function.Supplier;

/**
 * Created by john on 9/22/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class PgDaoFactory implements DaoFactory {

    private UserDao userDao;
    private ChatMessageDao messageDao;

    public PgDaoFactory(Supplier<Connection> connectionSupplier){
        this.userDao = new PgUserDao(connectionSupplier);
        this.messageDao = new PgChatMessageDao(connectionSupplier);
    }

    @Override
    public UserDao getUserDao() { return userDao; }

    @Override
    public ChatMessageDao getChatMessageDao() {
        return messageDao;
    }

    @Override
    public UserProfileDao getProfileDao() {
        return null;
    }
}
