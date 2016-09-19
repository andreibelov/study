package ru.belov.pubsub.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by john on 9/19/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
@Accessors(chain = true)
public class ChatMessage implements Serializable {

    // Constants ----------------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------

    private long id;
    private UUID uuid;
    private String text;
    private long userId;
    private String sender;

    // Methods ------------------------------------------------------------------------------------

    @Override
    public String toString(){
        return text;
    }
}
