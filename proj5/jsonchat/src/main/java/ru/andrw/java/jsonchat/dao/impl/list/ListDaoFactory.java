package ru.andrw.java.jsonchat.dao.impl.list;

import ru.andrw.java.jsonchat.dao.ChatMessageDao;
import ru.andrw.java.jsonchat.dao.DaoFactory;
import ru.andrw.java.jsonchat.dao.UserDao;
import ru.andrw.java.jsonchat.model.User;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by john on 9/22/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class ListDaoFactory implements DaoFactory {

    private UserDao userDao;
    private ChatMessageDao messageDao;

    public ListDaoFactory(){

        AtomicLong al = new AtomicLong(0L);
        List<User> userList = new CopyOnWriteArrayList<User>();
        userList.add((new User()).setId(al.get())
                        .setEmail("andrei.belov@mail.ru")
                        .setLogin("andrei.belov@mail.ru")
                        .setPassword("admin"));

        this.userDao = new ListUserDao(userList,al);
    }

    @Override
    public UserDao getUserDao() {
        return userDao;
    }

    @Override
    public ChatMessageDao getChatMessageDao() {
        return null;
    }
}
