package com.example.chat.dao.impl.postgres;

import com.example.chat.dao.DaoException;
import com.example.chat.dao.MessageDao;
import com.example.chat.model.Message;
import com.example.chat.util.DbUtil;
import org.postgresql.util.PGobject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Created by john on 8/12/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class PgMessageDao implements MessageDao {

    private Connection connection;
    
    public PgMessageDao(Supplier<Connection> connectionSupplier) {
        connection = connectionSupplier.get();
    }

    @Override
    public Message addMessage(Message message) throws DaoException{
        PreparedStatement preparedStatement = null;
        PGobject pgUuid = new PGobject();
        try {
            preparedStatement = connection
                    .prepareStatement("INSERT INTO MESSAGES(text,timestamp,user_from,chat_room_id,uuid) " +
                            "VALUES (?, ?, ?, ?, ?)");
            pgUuid.setType("uuid");
            pgUuid.setValue(message.getUuid().toString());
            preparedStatement.setString(1, message.getText());
            preparedStatement.setDate(2, new Date(message.getTimestamp().getTime()));
            preparedStatement.setInt(3, message.getFromUserId());
            preparedStatement.setInt(4, message.getChatRoomId());
            preparedStatement.setObject(5, pgUuid);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (preparedStatement != null) preparedStatement.close();
            } catch (Exception e) { throw new DaoException(e.getMessage(),e);};
        }
        return this.getMessageByUuid(message.getUuid().toString());

    }

    @Override
    public Message getMessageByUuid(String uuid) {
        Message message = new Message();
        PGobject pgUuid = new PGobject();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {preparedStatement = connection.
                    prepareStatement("SELECT ID,UUID,CHAT_ROOM_ID,TEXT,USER_FROM,TIMESTAMP,IS_SPAM " +
                            "FROM MESSAGES WHERE UUID=?");
            pgUuid.setType("uuid");
            pgUuid.setValue(uuid);
            preparedStatement.setObject(1, pgUuid);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                message.setMessageId(rs.getLong("id"))
                        .setUuid(rs.getObject("uuid", java.util.UUID.class))
                        .setChatRoomId(rs.getInt("chat_room_id"))
                        .setText(rs.getString("text"))
                        .setFromUserId(rs.getInt("user_from"))
                        .setTimestamp(rs.getDate("timestamp"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {};
            try { if (preparedStatement != null) preparedStatement.close();
            } catch (Exception e) {};
        }

        return message;
    }

    @Override
    public void deleteMessage(long messageId) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection
                    .prepareStatement("DELETE FROM MESSAGES WHERE ID=?");
            preparedStatement.setLong(1, messageId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (preparedStatement != null) preparedStatement.close();
            } catch (Exception e) {};
        }
    }

    @Override
    public void editMessage(Message message) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection
                    .prepareStatement("UPDATE MESSAGES " +
                            "SET TEXT=?, TIMESTAMP=?, USER_FROM=?, CHAT_ROOM_ID=?" +
                            "WHERE ID=?");
            preparedStatement.setString(1, message.getText());
            preparedStatement.setDate(2, new Date(message.getTimestamp().getTime()));
            preparedStatement.setInt(3, message.getFromUserId());
            preparedStatement.setInt(4, message.getChatRoomId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (preparedStatement != null) preparedStatement.close();
            } catch (Exception e) {};
        }
    }

    @Override
    public void markAsSpam(long messageId) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection
                    .prepareStatement("UPDATE MESSAGES " +
                            "SET IS_SPAM=TRUE " +
                            "WHERE ID=?");
            preparedStatement.setLong(1, messageId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (preparedStatement != null) preparedStatement.close();
            } catch (Exception e) {};
        }
    }

    @Override
    public Message getMessageById(long messageId) {
        PreparedStatement preparedStatement = null;
        Message message = new Message();
        ResultSet rs = null;
        try {
            preparedStatement = connection.
                    prepareStatement("SELECT * " +
                            "FROM MESSAGES WHERE ID=?");
            preparedStatement.setLong(1, messageId);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                message.setMessageId(rs.getLong("id"))
                        .setUuid(rs.getObject("uuid", java.util.UUID.class))
                        .setChatRoomId(rs.getInt("chat_room_id"))
                        .setText(rs.getString("text"))
                        .setFromUserId(rs.getInt("user_from"))
                        .setTimestamp(rs.getDate("timestamp"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {};
            try { if (preparedStatement != null) preparedStatement.close();
            } catch (Exception e) {};
        }

        return message;
    }

    @Override
    public List<Message> getMessagesByPageNum(int roomId, int pageNum) {
        List<Message> messages = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            preparedStatement = connection
                    .prepareStatement("SELECT ID,UUID,CHAT_ROOM_ID,TEXT,USER_FROM,TIMESTAMP " +
                            "FROM MESSAGES " +
                            "WHERE CHAT_ROOM_ID = ? AND MESSAGES.IS_SPAM IS NOT TRUE " +
                            "LIMIT 30 OFFSET 30*?");
            preparedStatement.setInt(1, roomId);
            preparedStatement.setInt(2, pageNum);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Message message = new Message()
                        .setMessageId(rs.getLong("id"))
                            .setUuid(rs.getObject("uuid", java.util.UUID.class))
                            .setChatRoomId(rs.getInt("chat_room_id"))
                            .setText(rs.getString("text"))
                            .setFromUserId(rs.getInt("user_from"))
                            .setTimestamp(rs.getDate("timestamp"));
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {};
            try { if (preparedStatement != null) preparedStatement.close();
            } catch (Exception e) {};
        }

        return messages;
    }

    @Override
    public List<Message> getMessagesByUser(int userId) {
        List<Message> messages = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            preparedStatement = connection
                    .prepareStatement("SELECT ID,UUID,CHAT_ROOM_ID,TEXT,USER_FROM,TIMESTAMP,IS_SPAM " +
                            "FROM MESSAGES " +
                            "WHERE USER_FROM = ?");
            preparedStatement.setInt(1, userId);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Message message = new Message()
                        .setMessageId(rs.getLong("id"))
                        .setUuid(rs.getObject("uuid", java.util.UUID.class))
                        .setChatRoomId(rs.getInt("chat_room_id"))
                        .setText(rs.getString("text"))
                        .setFromUserId(rs.getInt("user_from"))
                        .setTimestamp(rs.getDate("timestamp"));
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {};
            try { if (preparedStatement != null) preparedStatement.close();
            } catch (Exception e) {};
        }

        return messages;
    }

}
