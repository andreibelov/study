package ru.andrw.java.chat.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by john on 6/24/2016.
 *
 */

@Setter
@Getter
public final class User implements Serializable {

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

    // Object overrides ---------------------------------------------------------------------------

    @Override
    public boolean equals(Object other) {
        return (other instanceof User) && (id != null) ? id.equals(((User) other).id) : (other == this);
    }

    @Override
    public int hashCode() {
        return (id != null)
                ? (this.getClass().hashCode() + id.hashCode())
                : super.hashCode();
    }

    @Override
    public String toString() {
        return String.format("User[id=%d,email=%s,firstname=%s,lastname=%s,birthdate=%s]",
                id, email, firstname, lastname, birthday);
    }

    public User map(Long id, String email, String login, String password, String firstname, String lastname, Date birthday){
        this.id = id;
        this.email = email;
        this.login = login;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        return this;
    }
}
