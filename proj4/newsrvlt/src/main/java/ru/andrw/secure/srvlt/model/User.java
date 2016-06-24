package ru.andrw.secure.srvlt.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by john on 6/24/2016.
 *
 */

public class User implements Serializable {

    // Constants ----------------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------

    @Getter @Setter private Long id;
    @Getter @Setter private String email;
    @Getter @Setter private String password;
    @Getter @Setter private String firstname;
    @Getter @Setter private String lastname;
    @Getter @Setter private Date birthdate;

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
                id, email, firstname, lastname, birthdate);
    }

}