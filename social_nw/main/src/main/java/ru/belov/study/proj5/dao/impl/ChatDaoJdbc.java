package ru.belov.study.proj5.dao.impl;

import ru.belov.study.proj5.dao.ChatDao;
import ru.belov.study.proj5.dao.DaoException;
import ru.belov.study.proj5.dao.JdbcDao;
import ru.belov.study.proj5.model.account.User;
import ru.belov.study.proj5.model.chat.ChatMessage;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Created by john on 7/22/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class ChatDaoJdbc implements ChatDao, JdbcDao {

    private Supplier<Connection> connectionSupplier;

    public ChatDaoJdbc(Supplier<Connection> connectionSupplier) {
        this.connectionSupplier = connectionSupplier;
    }

    @Override
    public ChatMessage create(String text, User user) throws IllegalArgumentException, DaoException {

        return new ChatMessage().setFrom(user)
                .setTimestamp(new Date())
                .setText(text)
                .setUuid(UUID.randomUUID());

    }

    @Override
    public Optional<ChatMessage> find(Long id) throws DaoException {
        return null;
    }

    @Override
    public Optional<ChatMessage> find(String text) throws DaoException {
        return null;
    }

    @Override
    public Optional<ChatMessage> find(User from) throws DaoException {
        return null;
    }

    @Override
    public List<ChatMessage> list(Long indx, int amount) throws DaoException {
        return null;
    }

    @Override
    public Connection getConnection() {
        return connectionSupplier.get();
    }
}
