package example.dao;

import example.model.User;

import java.util.List;

/**
 * Created by john on 8/12/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public interface UserDao {
    public void addUser(User user);
    public void deleteUser(int userId);
    public void updateUser(User user);
    public List<User> getAllUsers();
    public User getUserById(int userId);
}
