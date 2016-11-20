package wastelander.model.common;

import java.io.Serializable;
import java.net.URI;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by john on 11/5/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
@Embeddable
@Accessors(chain = true)
public class Attachment implements Serializable {

    // Constants -----------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ----------------------------------------------------------------

    @Column(nullable=false, unique = true)
    private UUID uuid;
    @Enumerated(EnumType.STRING)
    private AttType type;

    public enum AttType {
        DOCUMENT,
        IMG,
        AUDIO,
        VIDEO,
        POST,
        IMESSAGE
    }

    private URI uri;


}
