package ru.andrw.java.chat.dao.list;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import ru.andrw.java.chat.dao.exeptions.DAOException;
import ru.andrw.java.chat.dao.ifaces.UserDAO;
import ru.andrw.java.chat.model.User;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * Created by john on 7/2/2016.
 * Implements UserDAO based on the List
 */

public enum UserDAOList implements UserDAO {
    INSTANCE((ArrayList<User>) Arrays.asList(new User()));

    private final List<User> userList;

    private UserDAOList(ArrayList<User> list_of_users){
        this.userList = list_of_users;
    }

    public static UserDAOList getINSTANCE(){
        synchronized (INSTANCE) {
            return INSTANCE;
        }
    }

    @Override
    public User find(Long id) throws DAOException {

        return null;
    }

    @Override
    public User find(String login, String password) throws DAOException {
        return null;
    }

    @Override
    public List<User> list() throws DAOException {
        return null;
    }

    @Override
    public void create(User user) throws IllegalArgumentException, DAOException {

    }

    @Override
    public void update(User user) throws IllegalArgumentException, DAOException {

    }

    @Override
    public void delete(User user) throws DAOException {

    }

    @Override
    public boolean existEmail(String email) throws DAOException {
        return false;
    }

    @Override
    public void changePassword(User user) throws DAOException {

    }

    public static Document fromXML(String xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
    }
}
