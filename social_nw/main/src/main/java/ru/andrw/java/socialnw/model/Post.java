package ru.andrw.java.socialnw.model;

import org.eclipse.persistence.annotations.Index;

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
import javax.persistence.OneToMany;

import lombok.Data;

/**
 * Created by john on 10/10/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
@Entity
public class Post implements Serializable {

    // Constants -----------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ----------------------------------------------------------------

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Index @Column(nullable=false, unique = true, columnDefinition = "binary(16)")
    private UUID uuid;
    private Timestamp created;
    private Long profileid;
    private String content;
    private Long likes;
    @ElementCollection
    private List<Attach> attachments;;

}
