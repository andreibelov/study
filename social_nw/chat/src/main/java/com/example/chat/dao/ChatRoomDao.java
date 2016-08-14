package com.example.chat.dao;

import com.example.chat.model.User;

import java.util.List;

/**
 * Created by john on 8/12/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public interface ChatRoomDao {

    public void newRoom(String name);
    public String getRoomNameById(int roomId);
    public void deleteRoom(int roomId);
    public void addUser(User user, int roomId);
    public void kickUser(int userId, int roomId);
    public List<User> getListOfUsers(int roomId);

}
