package ru.andrw.java.security;

import java.io.Serializable;

/**
 * Created by john on 6/23/2016.
 *
 */

public class User implements Serializable{
    // Constants ----------------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------

    private Long id;
    private String login;
    private String password;

    @Override
    public boolean equals(Object other) {
        return (other instanceof User) && (id != null) ? id.equals(((User) other).id) : (other == this);
    }

    @Override
    public String toString() {
        return String.format("User[id=%d,login=%s]", id, login);
    }
}