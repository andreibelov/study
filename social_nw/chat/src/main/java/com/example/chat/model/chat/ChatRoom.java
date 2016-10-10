package com.example.chat.model.chat;

import com.example.chat.model.ChatUsers;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by john on 8/12/2016.
 * Represents User object model
 */
@Data
public class ChatRoom implements Serializable {

    // Constants ----------------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------

    private int room_id;
    private String name;
    private ChatUsers chatUsers;
    private List<ChatMessage> messages;
}
