package ru.andrw.secure.srvlt.dao;

import ru.andrw.secure.srvlt.model.User;
import static java.lang.Math.toIntExact;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by john on 6/24/2016.
 *
 */

public class UserDAOList implements UserDAO, Serializable {

    // Constants ----------------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Vars ---------------------------------------------------------------------------------------

    private User[] userlist;
    {
        userlist = new User[1];
        User user0 = new User();
        user0.setId(0L);
        user0.setEmail("admin");
        user0.setPassword("");
        user0.setFirstname("Andrei");
        user0.setLastname("Belov");
        user0.setBirthdate(new Date(1989,8,5));
        userlist[0] = user0;
    }

    // Actions ------------------------------------------------------------------------------------

    private UserDAOList(){
        //make it private
    }

    public static UserDAO getInstance() {
        return new UserDAOList();
    }

    @Override
    public User find(Long id) throws DAOException {
        User user = null;
        try {
            user = userlist[toIntExact(id)];
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
        return user;
    }

    @Override
    public User find(String email, String password) throws DAOException {
        User user = null;
        for (User entry : userlist ) {
            if (entry.getEmail().equals(email)){
                if (entry.getPassword().equals(password)){
                    return entry;
                }
            }
        }
        return null;
    }

    private static User map(Long id, String email, String firstname, String lastname, Date birthdate) {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setBirthdate(birthdate);
        return user;
    }
}
