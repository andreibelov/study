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
    private Date crated;

}
