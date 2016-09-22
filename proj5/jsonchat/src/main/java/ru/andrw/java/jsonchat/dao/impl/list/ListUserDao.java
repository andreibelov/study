package ru.andrw.java.jsonchat.dao.impl.list;

import ru.andrw.java.jsonchat.dao.DaoException;
import ru.andrw.java.jsonchat.dao.UserDao;
import ru.andrw.java.jsonchat.model.chat.ChatMessage;
import ru.andrw.java.jsonchat.model.User;

import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

import static java.util.Optional.ofNullable;

/**
 * Created by john on 7/3/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class ListUserDao implements UserDao {

    private List<User> users;
    private AtomicLong al;

    ListUserDao(List<User> users, AtomicLong al){
        this.users = users;
        this.al = al;
    }


    @Override
    public Optional<User> getUserById(Long id) throws DaoException {
       return users.stream().filter(user -> id.equals(user.getId()))
               .findAny();
    }

    @Override
    public Optional<User> findUser(String login, String pass) throws DaoException {
        return users.stream().filter(u -> u.getLogin().equals(login))
                .findAny().filter(u -> u.getPassword().equals(pass));
    }

    @Override
    public void addUser(User user) throws DaoException {
        if(!userValidator(user)) throw new DaoException();
        else users.add(user.setId(al.incrementAndGet()));
    }

    private boolean userValidator(User user){
        return ofNullable(user).filter(u -> u != null)
                .filter(u -> u.getLogin() != null)
                .filter(u -> u.getPassword() != null)
                .filter(u -> u.getEmail() != null)
                .isPresent();
    }

    @Override
    public void deleteUser(Long userId) throws DaoException {

        IntStream.range(0, users.size())
                .filter(i -> users.get(i).getId().equals(userId))
                .findAny().ifPresent(i -> users.remove(i));
    }

    @Override
    public void updateUser(User user) throws DaoException {
        if(!userValidator(user)) throw new DaoException();
        else {
            IntStream.range(0, users.size())
                    .filter(i -> users.get(i).getId().equals(user.getId()))
                    .findAny().ifPresent(i -> users.set(i,user));
        }
    }

    @Override
    public List<User> getAllUsers() throws DaoException {
        return users;
    }


}
