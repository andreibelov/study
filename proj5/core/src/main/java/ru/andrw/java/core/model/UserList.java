package ru.andrw.java.core.model;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.*;
import javax.xml.bind.*;
import javax.xml.bind.annotation.*;

/**
 * Created by john on 7/2/2016.
 * This class represents list of users
 *
 */

@Setter
@Getter
@XmlRootElement(name="users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserList {
    @XmlElement(name="user")
    private List<User> userList = null;;

    private UserList() {
        userList = new ArrayList<User>();
    }

    public static UserList getInstance(){
        return new UserList();
    }
}