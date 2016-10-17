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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by john on 10/16/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
@MappedSuperclass
@Accessors(chain = true)
public class Conversation implements Serializable{

    // Constants ------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties -----------------------------------------------------------------

    private Long id;
    @Index
    private Date started;
    @Index @Column(nullable=false)
    private Date lastUpdate;
    @Index @Column(nullable=false, unique = true, columnDefinition = "binary(16)")
    private UUID uuid;
}
