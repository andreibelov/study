package ru.belov.study.proj5.dao;

import ru.belov.study.proj5.model.account.User;
import ru.belov.study.proj5.model.chat.ChatMessage;


import java.util.List;
import java.util.Optional;

/**
 *
 * This interface represents a contract for a CRUD DAO for the {@link ChatMessage} model.
 * Created by john on 7/22/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public interface ChatDao {

    ChatMessage create(String text, User user) throws IllegalArgumentException, DaoException;

    Optional<ChatMessage> find(Long id) throws DaoException;
    Optional<ChatMessage> find(String text) throws DaoException;
    Optional<ChatMessage> find(User from) throws DaoException;

    /**
     * Returns the list of {@link ChatMessage} from the database matching parameters.
     * @param indx from pointer.
     * @param amount The amount of messages to be returned since index.
     * @return The list of messages regarding to range provided.
     * @throws DaoException If something fails at database level.
     */
    List<ChatMessage> list(Long indx, int amount) throws DaoException;

}
