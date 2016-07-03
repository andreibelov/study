package ru.andrw.java.chat.dao.list;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import ru.andrw.java.chat.dao.exeptions.DAOException;
import ru.andrw.java.chat.dao.ifaces.UserDAO;
import ru.andrw.java.chat.model.User;

import javax.xml.bind.annotation.*;
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
@XmlRootElement(name="root")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDAOList implements UserDAO {
    @XmlElementWrapper(name="users")
    @XmlElement(name="user")
    private List<User> userList;
    
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


}
