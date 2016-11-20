package wastelander.model.chat;

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
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.experimental.Accessors;
import wastelander.model.common.Attachment;
import wastelander.model.common.User;

/**
 * Created by john on 11/5/2016.
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

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, unique = true, columnDefinition = "binary(16)")
    private UUID uuid;
    private Date created;
    private String content;
    @Column(nullable=true)
    private Long convoId;
    @Column(nullable=false, columnDefinition = "binary(16)")
    private UUID convoUuid;
    @ManyToOne
    private User user;
    @Column(nullable=false)
    private boolean isRead = false;
    @ElementCollection
    private List<Attachment> attachments;
}
