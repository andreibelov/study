package com.example.chat.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by john on 8/12/2016.
 * Represents User list object model
 */
@Data
public class ChatUsers implements Serializable {

    // Constants ----------------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------

    List<User> userList;
}
