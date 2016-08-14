package ru.belov.study.proj5.model.chat;

/**
 * Created by john on 8/8/2016.
 */

import lombok.Data;
import lombok.experimental.Accessors;
import ru.belov.study.proj5.model.account.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by john on 7/22/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
@Entity
@Accessors(chain = true)
public class ChatMessage implements Serializable {

    // Constants ----------------------------------------------------------------------------------
    @Version
    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------
    @Id
    @GeneratedValue
    private Long id;
    private String text;
    @ManyToOne
    private User from;
    @Column(unique=true)
    private UUID uuid;
    private Date timestamp;
}
