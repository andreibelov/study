package ru.andrw.java.chat.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by john on 7/1/2016.
 *
 */
public class Message implements Serializable {
    // Constants ----------------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------

    @Getter @Setter private Long id;
    @Getter @Setter private User creator;
    @Getter @Setter private UserGroup userGroup;
    @Getter @Setter private String text;
    @Getter @Setter private Date creationDate;

    // Object overrides ---------------------------------------------------------------------------

    @Override
    public boolean equals(Object other) {
        return (other instanceof User) && (id != null) ? id.equals(((Message) other).id) : (other == this);
    }

    @Override
    public int hashCode() {
        return (id != null)
                ? (this.getClass().hashCode() + id.hashCode())
                : super.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Message[id=%d,email=%s,name=%s,lastname=%s,birthdate=%s]",
                id, creator, userGroup);
    }

}
