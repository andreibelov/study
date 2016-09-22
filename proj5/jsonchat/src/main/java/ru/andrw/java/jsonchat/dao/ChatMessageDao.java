package ru.andrw.java.jsonchat.dao;

import ru.andrw.java.jsonchat.model.chat.ChatMessage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by john on 9/22/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public interface ChatMessageDao {

    public void createMessage(ChatMessage message) throws DaoException;
    public List<ChatMessage> listAll();
    public Optional<ChatMessage> getMessage(UUID uuid) throws DaoException;
    public List<ChatMessage> findMessage(String text) throws DaoException;
    public void deleteMessage(ChatMessage message) throws DaoException;
}
