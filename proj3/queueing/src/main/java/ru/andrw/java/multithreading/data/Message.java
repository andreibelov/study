package ru.andrw.java.multithreading.data;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by john on 7/5/2016.
 */
@Getter
@Setter
@Accessors(chain = true)
public class Message implements Serializable {

    // Constants ----------------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------

    private Long id;
    private UUID uuid;
    private int from;
    private String text;
    private Date crated;

    // Object overrides ---------------------------------------------------------------------------

    /**
     * The message ID is unique for each Message. So this should compare Message by ID only.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other) {
        return (other instanceof Message) && (id != null)
                ? id.equals(((Message) other).id)
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
        return "[from #"+from+"] "+text;
    }
}
