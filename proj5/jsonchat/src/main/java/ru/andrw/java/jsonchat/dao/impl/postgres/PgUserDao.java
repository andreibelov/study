package ru.andrw.java.jsonchat.dao.impl.postgres;

import ru.andrw.java.jsonchat.dao.DaoException;
import ru.andrw.java.jsonchat.dao.UserDao;
import ru.andrw.java.jsonchat.model.User;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

/**
 * Created by john on 9/22/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class PgUserDao implements UserDao {

    private Connection connection;

    PgUserDao(Supplier<Connection> connectionSupplier) {
        connection = connectionSupplier.get();
    }

    @Override
    public Optional<User> findUser(String email, String password) throws DaoException {
        User user = null;
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * FROM USERS WHERE EMAIL=? AND PASSWORD=?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getLong("userid"));
                user.setLogin(rs.getString("login"));
                user.setEmail(email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ofNullable(user);
    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public void deleteUser(Long userId) {

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public Optional<User> getUserById(Long userId) throws DaoException {
        return empty();
    }


}