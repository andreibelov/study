package com.example.chat.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by john on 8/12/2016.
 * Represents Message object model
 */
@Data
@Accessors(chain = true)
public class Message implements Serializable {

    // Constants ----------------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------

    private long messageId;
    private String text;
    private Date timestamp;
    private boolean isSpam;
    private int fromUserId;
    private int chatRoomId;

}
