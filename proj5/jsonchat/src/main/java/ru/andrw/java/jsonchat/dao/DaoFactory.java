package ru.andrw.java.jsonchat.dao;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

/**
 * Created by john on 7/3/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public interface DaoFactory {
    public UserDao getUserDao();
    public ChatMessageDao getChatMessageDao();
    public UserProfileDao getProfileDao();
}