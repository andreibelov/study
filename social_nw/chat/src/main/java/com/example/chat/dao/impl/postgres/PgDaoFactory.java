package com.example.chat.dao.impl.postgres;

import com.example.chat.dao.ChatMessageDao;
import com.example.chat.dao.ChatRoomDao;
import com.example.chat.dao.DaoFactory;
import com.example.chat.dao.MessageDao;
import com.example.chat.dao.UserDao;
import com.example.chat.dao.UserProfileDao;

import java.sql.Connection;
import java.util.function.Supplier;

/**
 * Created by john on 8/14/2016.
 * PostgresQL DaoFactory implementation
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class PgDaoFactory implements DaoFactory {

    private ChatRoomDao chatDao;
    private MessageDao messageDao;
    private UserDao userDao;

    public PgDaoFactory(Supplier<Connection> connectionSupplier){
        this.chatDao = new PgChatRoomDao(connectionSupplier);
        this.messageDao = new PgMessageDao(connectionSupplier);
        this.userDao = new PostgreUserDao(connectionSupplier);
    }

    @Override
    public UserDao getUserDao() {
        return this.userDao;
    }

    @Override
    public ChatMessageDao getChatMessageDao() {
        return null;
    }

    @Override
    public UserProfileDao getProfileDao() {
        return null;
    }
}
