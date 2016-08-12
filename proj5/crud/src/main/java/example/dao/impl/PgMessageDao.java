package example.dao.impl;

import example.dao.MessageDao;
import example.model.Message;
import example.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by john on 8/12/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class PgMessageDao implements MessageDao {

    private Connection connection;

    public PgMessageDao() {
        connection = DbUtil.getConnection();
    }

    @Override
    public void newMessage(Message message) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO MESSAGES(text,timestamp,user_from,chat_room_id) " +
                            "VALUES (?, ?, ?, ? )");
            preparedStatement.setString(1, message.getText());
            preparedStatement.setDate(2, new java.sql.Date(message.getTimestamp().getTime()));
            preparedStatement.setInt(3, message.getFromUserId());
            preparedStatement.setInt(4, message.getChatRoomId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMessage(long messageId) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM MESSAGES WHERE ID=?");
            preparedStatement.setLong(1, messageId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editMessage(Message message) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE MESSAGES " +
                            "SET TEXT=?, TIMESTAMP=?, USER_FROM=?, CHAT_ROOM_ID=?" +
                            "WHERE ID=?");
            preparedStatement.setString(1, message.getText());
            preparedStatement.setDate(2, new java.sql.Date(message.getTimestamp().getTime()));
            preparedStatement.setInt(3, message.getFromUserId());
            preparedStatement.setInt(4, message.getChatRoomId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void markAsSpam(long messageId) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE MESSAGES " +
                            "SET IS_SPAM=TRUE " +
                            "WHERE ID=?");
            preparedStatement.setLong(1, messageId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Message getMessageById(long messageId) {
        Message message = new Message();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * " +
                            "FROM MESSAGES WHERE ID=?");
            preparedStatement.setLong(1, messageId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                message.setMessageId(rs.getLong("id"));
                message.setChatRoomId(rs.getInt("chat_room_id"));
                message.setText(rs.getString("text"));
                message.setFromUserId(rs.getInt("user_from"));
                message.setTimestamp(rs.getDate("timestamp"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return message;
    }

    @Override
    public List<Message> getMessagesByPageNum(int roomId, int pageNum) {
        List<Message> messages = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT ID,TEXT,USER_FROM,TIMESTAMP " +
                            "FROM MESSAGES " +
                            "WHERE CHAT_ROOM_ID = ? AND MESSAGES.IS_SPAM IS NOT TRUE " +
                            "LIMIT 30 OFFSET 30*?");
            preparedStatement.setInt(1, roomId);
            preparedStatement.setInt(2, pageNum);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Message message = new Message();
                message.setMessageId(rs.getLong("id"));
                message.setText(rs.getString("text"));
                message.setFromUserId(rs.getInt("user_from")); //TODO INNER JOIN firstname and lastname;
                message.setTimestamp(rs.getDate("timestamp"));
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }

    @Override
    public List<Message> getMessagesByUser(int userId) {
        List<Message> messages = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT ID,CHAT_ROOM_ID,TEXT,USER_FROM,TIMESTAMP,IS_SPAM " +
                            "FROM MESSAGES " +
                            "WHERE USER_FROM = ?");
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Message message = new Message();
                message.setMessageId(rs.getLong("id"));
                message.setChatRoomId(rs.getInt("chat_room_id"));
                message.setText(rs.getString("text"));
                message.setFromUserId(rs.getInt("user_from"));
                message.setTimestamp(rs.getDate("timestamp"));
                message.setSpam(rs.getBoolean("is_spam"));
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }

}
