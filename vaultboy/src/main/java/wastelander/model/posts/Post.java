package wastelander.model.posts;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
public class Post implements Serializable {

    // Constants -----------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ----------------------------------------------------------------

    @Column(nullable=false, unique = true, columnDefinition = "binary(16)")
    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private UUID uuid;
    private Timestamp created;
    @ManyToOne
    private User user;
    private String content;
    private Long likes;
    @ElementCollection
    private List<Attachment> attachments;;

}
