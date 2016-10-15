package ru.andrw.java.socialnw.model.chat;


import java.io.Serializable;
import java.security.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.function.BooleanSupplier;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import ru.andrw.java.socialnw.model.Attach;

/**
 * Created by john on 9/30/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
@Entity
public class IMessage implements Serializable {

    // Constants ------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties -----------------------------------------------------------------

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique=true, nullable=false)
    private UUID uuid;
    private Timestamp created;
    private String content;
    private UUID chatRoom;
    private boolean isRead;
    @ElementCollection
    private List<Attach> attachments;;

}
