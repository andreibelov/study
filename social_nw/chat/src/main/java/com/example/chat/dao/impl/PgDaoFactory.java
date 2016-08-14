package com.example.chat.dao.impl;

import com.example.chat.dao.ChatRoomDao;
import com.example.chat.dao.DaoFactory;
import com.example.chat.dao.MessageDao;
import com.example.chat.dao.UserDao;

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
    public MessageDao getMessageDao() {
        return this.messageDao;
    }

    @Override
    public UserDao getUserDao() {
        return this.userDao;
    }

    @Override
    public ChatRoomDao getChatDao() {
        return this.chatDao;
    }
}
