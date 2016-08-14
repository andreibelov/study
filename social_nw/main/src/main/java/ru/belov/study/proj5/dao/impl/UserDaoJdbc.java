package ru.belov.study.proj5.dao.impl;

import ru.belov.study.proj5.dao.DaoException;
import ru.belov.study.proj5.dao.JdbcDao;
import ru.belov.study.proj5.dao.UserDao;
import ru.belov.study.proj5.model.account.User;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by john on 7/22/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class UserDaoJdbc implements UserDao, JdbcDao {

    private Supplier<Connection> connectionSupplier;

    public UserDaoJdbc(Supplier<Connection> connectionSupplier) {
        this.connectionSupplier = connectionSupplier;
    }

    @Override
    public Connection getConnection() {
        return connectionSupplier.get();
    }

    @Override
    public Optional<User> find(Long id) throws DaoException {
        return null;
    }

    @Override
    public Optional<User> find(String login, String password) throws DaoException {
        String sql =
                "SELECT username, password " +
                        "FROM public.users t WHERE username = ? and password = ?";
        return mapPreparedStatement(sql, preparedStatement -> {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return Optional.ofNullable(!rs.next() ? null :
                        new User().setLogin(login).setEmail(password));
            }
        }).getOrThrowUnchecked();
    }

    @Override
    public List<User> listAll() throws DaoException {
        //language=PostgreSQL
        return null;
    }

    @Override
    public void add(User user) throws IllegalArgumentException, DaoException {

    }

    @Override
    public void update(User user) throws IllegalArgumentException, DaoException {

    }

    @Override
    public void delete(User user) throws DaoException {

    }

    @Override
    public boolean existEmail(String email) throws DaoException {
        return false;
    }

    @Override
    public void changePassword(User user, String newpass) throws DaoException {

    }
}
