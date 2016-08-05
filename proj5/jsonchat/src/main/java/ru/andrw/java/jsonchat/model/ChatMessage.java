package ru.andrw.java.jsonchat.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by john on 7/3/2016.
 */
@Data
public class ChatMessage implements Serializable {

    // Constants ----------------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------

    private Long id;
    private User creator;
    private String text;
    private Date created;

    // Object overrides ---------------------------------------------------------------------------

    /**
     * The message ID is unique for each Message. So this should compare Message by ID only.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other) {
        return (other instanceof ChatMessage) && (id != null)
                ? id.equals(((ChatMessage) other).id)
                : (other == this);
    }

    /**
     * The message ID is unique for each Message. So Message with same ID should return same hashcode.
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return (id != null)
                ? (this.getClass().hashCode() + id.hashCode())
                : super.hashCode();
    }

    /**
     * Returns the String representation of this Message. Not required, it just pleases reading logs.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return text;
    }



}
