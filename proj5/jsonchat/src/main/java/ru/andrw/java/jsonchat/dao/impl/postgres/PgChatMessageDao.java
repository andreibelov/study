package ru.andrw.java.jsonchat.dao.impl.postgres;

import ru.andrw.java.jsonchat.dao.ChatMessageDao;
import ru.andrw.java.jsonchat.dao.DaoException;
import ru.andrw.java.jsonchat.model.chat.ChatMessage;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Created by john on 9/22/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class PgChatMessageDao implements ChatMessageDao {

    private Connection connection;

    PgChatMessageDao(Supplier<Connection> connectionSupplier) {
        connection = connectionSupplier.get();
    }


    @Override
    public void createMessage(ChatMessage message) throws DaoException {

    }

    @Override
    public List<ChatMessage> listAll() {
        return null;
    }

    @Override
    public Optional<ChatMessage> getMessage(UUID uuid) throws DaoException {
        return null;
    }

    @Override
    public List<ChatMessage> findMessage(String text) throws DaoException {
        return null;
    }

    @Override
    public void deleteMessage(ChatMessage message) throws DaoException {

    }
}
