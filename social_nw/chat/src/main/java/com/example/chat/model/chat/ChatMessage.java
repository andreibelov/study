package com.example.chat.model.chat;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by john on 9/28/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
@Accessors(chain = true)
public class ChatMessage implements Serializable {

    // Constants ----------------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------

    private UUID uuid;
    private String text;
    private Long userId;
    private String sender;

    // Object overrides ---------------------------------------------------------------------------

    /**
     * Returns the String representation of this Message. Not required, it just pleases reading logs.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return text;
    }

}