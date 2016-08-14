package com.example.chat.dao;

import com.example.chat.model.Message;

import java.util.List;

/**
 * Created by john on 8/12/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public interface MessageDao {

    public void newMessage(Message message);
    public void deleteMessage(long messageId);
    public void editMessage(Message message);
    public void markAsSpam(long messageId);
    public Message getMessageById(long messageId);
    public List<Message> getMessagesByPageNum(int roomId, int pageNum);
    public List<Message> getMessagesByUser(int userId);

}
