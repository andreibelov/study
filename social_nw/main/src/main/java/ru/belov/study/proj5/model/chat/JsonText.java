package ru.belov.study.proj5.model.chat;

import lombok.Data;
import lombok.experimental.Accessors;
import org.eclipse.persistence.annotations.PrimaryKey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import java.time.Instant;

/**
 * Created by john on 7/22/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
@Entity
@Accessors(chain = true)
public class JsonText {
    // Constants ----------------------------------------------------------------------------------
    @Version
    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------
    @Id
    @GeneratedValue
    private Long id;
    private Instant timestamp;
    private String text;
}
