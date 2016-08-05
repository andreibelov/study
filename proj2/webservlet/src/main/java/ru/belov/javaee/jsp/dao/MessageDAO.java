package ru.belov.javaee.jsp.dao;

/**
 *
 */
import ru.belov.javaee.jsp.model.ChatUser;
import ru.belov.javaee.jsp.model.Message;
import ru.belov.javaee.jsp.model.User;

import java.util.List;
import java.util.Optional;

/**
 *
 * This interface represents a contract for a CRUD DAO for the {@link Message} model.
 * Created by john on 7/22/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public interface MessageDAO {

    Message create(String text, ChatUser chatUser) throws IllegalArgumentException, DAOException;

    Optional<Message> find(Long id) throws DAOException;
    Optional<Message> find(String text) throws DAOException;
    Optional<Message> find(ChatUser from) throws DAOException;

    /**
     * Returns the list of {@link Message} from the database matching parameters.
     * @param indx from pointer.
     * @param amount The amount of messages to be returned since index.
     * @return The list of messages regarding to range provided.
     * @throws DAOException If something fails at database level.
     */
    List<Message> list(Long indx, int amount) throws DAOException;

}
