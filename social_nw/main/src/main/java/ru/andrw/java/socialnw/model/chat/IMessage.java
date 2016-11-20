package ru.andrw.java.socialnw.model.chat;


import org.eclipse.persistence.annotations.Index;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;
import lombok.experimental.Accessors;
import ru.andrw.java.socialnw.model.Attach;

/**
 * Created by john on 9/30/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
@Entity
@Accessors(chain = true)
public class IMessage implements Serializable {

    // Constants ------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

     // Properties -----------------------------------------------------------------

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Index @Column(nullable=false, unique = true, columnDefinition = "binary(16)")
    private UUID uuid;
    @Index
    private Date created;
    private String content;
    @Index @Column(nullable=true)
    private Long convoId;
    @Index @Column(nullable=false, columnDefinition = "binary(16)")
    private UUID convoUuid;
    private Long sender;
    @Column(nullable=false)
    private boolean isRead = false;
    @ElementCollection
    private List<Attach> attachments;

}
