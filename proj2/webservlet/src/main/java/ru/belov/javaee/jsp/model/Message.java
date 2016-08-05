package ru.belov.javaee.jsp.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by john on 7/22/2016.
 */
@Data
@Entity
@Accessors(chain = true)
public class Message implements Serializable {

    // Constants ----------------------------------------------------------------------------------
    @Version
    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------
    private UUID uuid;
    private ChatUser from;
    private String text;
    private LocalDateTime timestamp;

}
