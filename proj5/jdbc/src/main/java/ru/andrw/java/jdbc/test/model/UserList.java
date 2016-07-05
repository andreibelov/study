package ru.andrw.java.jdbc.test.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by john on 7/5/2016.
 */
@Getter
@Setter
@XmlRootElement(name="root")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserList {
    public static List<User> users;


    private List<User> getUsers() {return users;}

    @XmlElementWrapper(name="users")
    @XmlElement(name="user")
    private void setUsers(List<User> users) {UserList.users = users;}
    private static AtomicLong al = new AtomicLong(3L);
    /*(resultSet\.get\p{L}*\(\")(\p{L}*)(\"\))*/

    public static User mapUser(Long id, Date birthday, String email, String login,
                               String phone, String password, String firstname,
                               String lastname) throws Exception {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setBirthday(birthday);
        return user;
    }

    public static User mapUser(String[] map) throws Exception {
        return mapUser(al.getAndIncrement(),new Date(),map[0],map[1],map[2],map[3],map[4],map[5]);
    }

    public static void add(User user){
        try {
            users.add(user);
        } catch (Exception ex){
            //Ohn-nom-nom
        }
    }

    public static void add(String[] map){
        try {
            users.add(mapUser(map));
        } catch (Exception ex){
            //Ohn-nom-nom
        }
    }

    public static User getUser(Long id){
        return users.stream().filter(user -> id.equals(user.getId()))
                .findAny().orElse(null);
    }

}