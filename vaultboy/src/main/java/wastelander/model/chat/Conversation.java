package wastelander.model.chat;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by john on 11/5/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
@MappedSuperclass
@Accessors(chain = true)
public class Conversation implements Serializable {

    // Constants ------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties -----------------------------------------------------------------

    private Date started;
    @Column(nullable=false)
    private Date lastUpdate;
    @Column(nullable=false, unique = true, columnDefinition = "binary(16)")
    private UUID uuid;
}
