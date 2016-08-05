package ru.belov.javaee.jsp.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.Tolerate;

import javax.persistence.Entity;
import java.util.Deque;
import java.util.concurrent.BlockingDeque;


/**
 * Created by john on 7/22/2016.
 */
@Data
@Entity
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ChatUser extends User {

    // Constants ----------------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------

    private String nickname;
    private String jsessionid;


}
