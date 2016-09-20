package ru.andrw.java.jsonchat.model.groups;

import lombok.Data;
import ru.andrw.java.jsonchat.model.chat.ChatRoom;

import java.io.Serializable;
import java.util.List;

/**
 * Created by john on 9/20/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
public class ChatRooms implements Serializable {

    // Constants ----------------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------

    private List<ChatRoom> rooms;

}
