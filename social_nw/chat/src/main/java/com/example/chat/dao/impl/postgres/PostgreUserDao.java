package com.example.chat.dao.impl.postgres;

import com.example.chat.dao.DaoException;
import com.example.chat.dao.UserDao;
import com.example.chat.model.User;
import com.example.chat.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by john on 8/12/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class PostgreUserDao implements UserDao {

    private Connection connection;

    public PostgreUserDao(Supplier<Connection> connectionSupplier) {
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
                user.setLogin(rs.getString("firstname"));
                user.setEmail(email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(user);
    }

    public void addUser(User user) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO USERS(FIRSTNAME,LASTNAME,DOB,EMAIL,PASSWORD) " +
                            "VALUES (?, ?, ?, ?, ?)");
            // Parameters start with 1
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(Long userId) throws DaoException {

    }

    public void deleteUser(int userId) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM USERS WHERE USERID=?");
            // Parameters start with 1
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE USERS SET FIRSTNAME=?, LASTNAME=?, DOB=?, EMAIL=?, PASSWORD=?" +
                            "WHERE USERId=?");
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setInt(6, user.getId().intValue());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM USERS");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("userid"));
                user.setLogin(rs.getString("firstname"));
                user.setEmail(rs.getString("email"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public Optional<User> getUserById(Long userId) throws DaoException {
        return null;
    }

    public User getUserById(int userId) {
        User user = new User();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * FROM USERS WHERE USERID=?");
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                user.setId(rs.getLong("userid"));
                user.setLogin(rs.getString("firstname"));
                user.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
