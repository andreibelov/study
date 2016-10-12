package ru.andrw.java.socialnw.model.chat;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import ru.andrw.java.socialnw.model.auth.User;

import static javax.persistence.FetchType.EAGER;

/**
 * Created by john on 9/30/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
@Entity
public class ChatRoom implements Serializable {

    // Constants ------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties -----------------------------------------------------------------

    @Id @GeneratedValue
    private Long id;
    private UUID uuid;
    private String description;
    @OneToMany
    private List<User> members;
}
