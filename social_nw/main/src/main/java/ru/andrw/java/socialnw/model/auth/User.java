package ru.andrw.java.socialnw.model.auth;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Created by john on 9/25/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
@Entity
@Accessors(chain = true)
@Inheritance(strategy= InheritanceType.JOINED)
public class User implements Serializable {

    // Constants -----------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Constructor ---------------------------------------------------------------

    public User(){
        this.accessLevel = 3;
    }

    // Properties ----------------------------------------------------------------
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String login;
    private String password;
    private Integer accessLevel;

    // Object overrides -----------------------------------------------------------

    /**
     * Returns the String representation of this User.
     * Not required, it just pleases reading logs.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("User[id=%d,email=%s,login=%s]",
                id, email, login);
    }
}

