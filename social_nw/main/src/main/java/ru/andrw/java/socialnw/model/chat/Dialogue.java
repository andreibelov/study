package ru.andrw.java.socialnw.model.chat;

import org.eclipse.persistence.annotations.Index;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;


/**
 * Created by john on 10/16/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
@Entity
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper=true, includeFieldNames=true)
public class Dialogue extends Conversation implements Serializable {

    // Constants ------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Constructors ---------------------------------------------------------------

    public Dialogue(final Long person1, final Long person2){
        this.person1 = person1;
        this.person2 = person2;
    }

    public Dialogue(){}

    // Properties -----------------------------------------------------------------

    @Id
    private Long id;
    @Index
    private Long person1;
    @Index
    private Long person2;

}
