package com.example.chat.dao.impl.postgres;

import com.example.chat.dao.ChatRoomDao;
import com.example.chat.model.User;
import com.example.chat.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by john on 8/12/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class PgChatRoomDao implements ChatRoomDao {

    private Connection connection;

    public PgChatRoomDao(Supplier<Connection> connectionSupplier) {
        connection = connectionSupplier.get();
    }

    @Override
    public void newRoom(String name) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO CHAT_ROOMS(CHAT_NAME) VALUES (?)");
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getRoomNameById(int roomId) {
        return null;
    }

    @Override
    public void deleteRoom(int roomId) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM CHAT_ROOMS WHERE CHAT_ROOM_ID=?");
            preparedStatement.setInt(1, roomId);
            preparedStatement.executeUpdate();

//            preparedStatement = connection
//                    .prepareStatement("DELETE FROM MESSAGES WHERE CHAT_ROOM_ID=?");
//            preparedStatement.setInt(1, roomId);
//            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUser(User user, int roomId) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO CHAT_USERS(CHAT_USER, CHAT_ROOM) VALUES (?,?)");
            preparedStatement.setInt(1, user.getId().intValue());
            preparedStatement.setInt(2, roomId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void kickUser(int userId, int roomId) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM CHAT_USERS WHERE CHAT_USER=? AND CHAT_ROOM=?");
            // Parameters start with 1
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, roomId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getListOfUsers(int roomId) {
        List<User> users = new ArrayList<User>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT u.userid, u.firstname, u.lastname " +
                    "FROM users u " +
                    "INNER JOIN chat_users ch_u ON ch_u.chat_user = u.userid " +
                    "WHERE ch_u.chat_room = ?");
            preparedStatement.setInt(1, roomId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("userid"));
                user.setLogin(rs.getString("firstname"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
