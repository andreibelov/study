package ru.belov.javaee.jsp.dao.impl;

import ru.belov.javaee.jsp.service.MD5Encrypter;
import ru.belov.javaee.jsp.dao.DAOException;
import ru.belov.javaee.jsp.dao.UserDAO;
import ru.belov.javaee.jsp.model.User;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Created by john on 7/22/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */

public class UserDAOList implements UserDAO {

    private static final UserDAOList INSTANCE;
    private static final AtomicLong al;
    private static final List<User> users;

    private UserDAOList(){}


    static {
        String path = "db.xml";
        UserList userlist;
        try(InputStream inputStream = UserList.class.getResourceAsStream(path))
        {
            userlist = (UserList) JAXBContext.newInstance(UserList.class)
                    .createUnmarshaller().unmarshal(inputStream);

        } catch (JAXBException | IOException e) {
            userlist = new UserList();
        }
        users = userlist.getUserList();
        al = userlist.getAl();
        INSTANCE = new UserDAOList();
    }

    public static UserDAOList getInstance(){
        return INSTANCE;
    }

    @Override
    public Optional<User> find(Long id) throws DAOException {
        return users.stream().filter(user -> id.equals(user.getId()))
                .findAny();
    }

    @Override
    public Optional<User> find(String login, String pwd) throws DAOException {
        return users.stream().filter(user -> login.equals(user.getLogin()))
                .filter(user -> pwd.equals(user.getPass())).findAny();
    }

    @Override
    public List<User> listAll() throws DAOException {
        return users;
    }

    @Override
    public void add(User user) throws IllegalArgumentException, DAOException {
        users.add(user);
    }

    public User create(User user) throws IllegalArgumentException, DAOException {
        return  null;
    }

    @Override
    public void update(User user) throws IllegalArgumentException, DAOException {

    }

    @Override
    public void delete(User user) throws DAOException {

    }

    @Override
    public boolean existEmail(String email) throws DAOException {
        return users.stream().filter(user -> email.equals(user.getEmail()))
                .findAny().isPresent();
    }

    @Override
    public void changePassword(User user, String newpass) throws DAOException {
        String pass = MD5Encrypter.encrypt(newpass);
        this.find(user.getId()).ifPresent(usr -> usr.setPass(pass));
    }
}
