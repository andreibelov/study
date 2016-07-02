package ru.andrw.java.core.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by john on 7/2/2016.
 *
 */
@Setter
@Getter
@XmlRootElement(name="user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Serializable {

    // Constants ----------------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------

    private Long id;
    private String email;
    private String login;
    private String phone;
    private String password;
    private String firstname;
    private String lastname;
    private Date birthday;


}
