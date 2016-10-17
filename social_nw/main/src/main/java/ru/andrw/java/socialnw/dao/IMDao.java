package ru.andrw.java.socialnw.dao;

import com.epam.courses.jf.dao.Dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ru.andrw.java.socialnw.model.chat.ChatRoom;
import ru.andrw.java.socialnw.model.chat.Conversation;
import ru.andrw.java.socialnw.model.chat.IMessage;

/**
 * Created by john on 10/12/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public interface IMDao extends Dao {
    public IMessage sendMessageToUser(IMessage message, Long senderId, Long receiverId) throws DaoException;
    public void inviteUser(Long userId, Long inviterId, Long chatRoomId) throws DaoException;
    public void kickUser(Long userId, Long moderatorId, Long chatRoomId) throws DaoException;
    public IMessage addNewIM(IMessage message) throws DaoException;
    public ChatRoom newChatRoom(ChatRoom chatRoom) throws DaoException;

    Map<Conversation, IMessage> getConversationList(Long userid, Integer offset, Integer limit);

    public List<IMessage> getIMsSinceDate(UUID convoUUID, Date from, Integer offset, Integer limit) throws DaoException;
    public void deleteMessage(IMessage message, Long userId) throws DaoException;
}
