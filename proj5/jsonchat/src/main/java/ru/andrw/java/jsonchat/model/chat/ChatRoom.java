package ru.andrw.java.jsonchat.model.chat;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by john on 9/20/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
@Accessors(chain = true)
public class ChatRoom implements Serializable {

    // Constants ----------------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------

    private UUID uuid;
    private String title;
    private String description;
    private String link;

    // Object overrides ---------------------------------------------------------------------------

    /**
     * Returns the String representation of this User. Not required, it just pleases reading logs.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("ChatRoom [uuid=%s, title: %s, description: %s]",
                uuid,title,description);
    }
}
