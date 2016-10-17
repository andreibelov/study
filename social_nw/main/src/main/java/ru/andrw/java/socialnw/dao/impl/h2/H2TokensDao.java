package ru.andrw.java.socialnw.dao.impl.h2;

import com.epam.courses.jf.dao.Dao;

import java.sql.Connection;
import java.util.Optional;
import java.util.function.Supplier;

import ru.andrw.java.socialnw.dao.TokensDao;
import ru.andrw.java.socialnw.model.auth.User;

/**
 * Created by john on 10/11/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
class H2TokensDao implements TokensDao, Dao {

    private final Supplier<Connection> supplier;
    private final String SPLITERATOR = ".";
    private final String SCHEMA_NAME = "PUBLIC";

    H2TokensDao(Supplier<Connection> connectionSupplier) {
        this.supplier = connectionSupplier;
    }

    @Override
    public void addToken(User user) {

    }

    @Override
    public void removeToken(String key) {

    }

    @Override
    public Optional<Long> findUserId(String key) {
        return null;
    }

    @Override
    public Connection getConnection() {
        return supplier.get();
    }
}
