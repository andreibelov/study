package ru.andrw.java.socialnw.model;

import org.eclipse.persistence.annotations.Index;

import java.io.Serializable;
import java.net.URI;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.andrw.java.socialnw.model.enums.AttType;

/**
 * Created by john on 10/10/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
@Embeddable
@Accessors(chain = true)
public class Attach implements Serializable {

    // Constants -----------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ----------------------------------------------------------------

    @Index
    @Column(nullable=false, unique = true, columnDefinition = "binary(16)")
    private UUID uuid;
    @Enumerated(EnumType.ORDINAL)
    private AttType type;
    private URI uri;

}
