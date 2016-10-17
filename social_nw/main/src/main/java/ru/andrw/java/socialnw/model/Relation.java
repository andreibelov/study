package ru.andrw.java.socialnw.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;
import ru.andrw.java.socialnw.model.enums.RowStatus;
import ru.andrw.java.socialnw.model.pkeys.RelationPK;

/**
 * Created by john on 10/11/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
@Entity
@IdClass(value=RelationPK.class)
public class Relation implements Serializable {

    // Constants -----------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ----------------------------------------------------------------

    @Id
    private Long idRequester;
    @Id
    private Long idRequestee;
    @Enumerated(EnumType.ORDINAL)
    private RowStatus status;
}
