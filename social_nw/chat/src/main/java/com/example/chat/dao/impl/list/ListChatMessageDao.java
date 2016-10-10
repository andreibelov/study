package com.example.chat.dao.impl.list;

import com.example.chat.dao.ChatMessageDao;
import com.example.chat.dao.DaoException;
import com.example.chat.model.chat.ChatMessage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;


import static java.util.Optional.ofNullable;

/**
 * Created by john on 9/22/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class ListChatMessageDao implements ChatMessageDao {

    private List<ChatMessage> messages;

    ListChatMessageDao(List<ChatMessage> list){
        this.messages = list;
    }

    @Override
    public void createMessage(ChatMessage message) throws DaoException {
        if(!messageValidator(message)) throw new DaoException();
        else messages.add(message);
    }

    private boolean messageValidator(ChatMessage message){
        return ofNullable(message).filter(m -> m != null)
                .filter(m -> m.getSender() != null)
                .filter(m -> m.getText() != null)
                .filter(m -> m.getUuid() != null)
                .isPresent();
    }

    @Override
    public List<ChatMessage> listAll() {
        return messages;
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
    public void deleteMessage(ChatMessage message) {

        IntStream.range(0, messages.size())
                .filter(i -> messages.get(i).getUuid().equals(message.getUuid()))
                .findAny().ifPresent(i -> messages.remove(i));
    }
}
