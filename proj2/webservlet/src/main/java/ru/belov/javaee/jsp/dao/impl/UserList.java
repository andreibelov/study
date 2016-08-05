package ru.belov.javaee.jsp.dao.impl;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.belov.javaee.jsp.model.User;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by john on 7/22/2016.
 */
@Data
@Accessors(chain = true)
@XmlRootElement(name="root")
@XmlAccessorType(XmlAccessType.FIELD)
class UserList implements Serializable {

    // Constants ----------------------------------------------------------------------------------
    @Version
    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------
    @XmlElementWrapper(name="users")
    @XmlElement(name="user")
    private final List<User> userList = new CopyOnWriteArrayList<User>();
    private final AtomicLong al = new AtomicLong(1000L);
}