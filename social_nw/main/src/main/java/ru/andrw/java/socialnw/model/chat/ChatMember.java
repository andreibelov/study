package ru.andrw.java.socialnw.model.chat;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.andrw.java.socialnw.model.auth.User;

/**
 * Created by john on 10/11/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
@Embeddable
@EqualsAndHashCode(callSuper = true)
public class ChatMember extends User implements Serializable{

    // Constants ------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties -----------------------------------------------------------------

    private UUID chatRoom;
}
