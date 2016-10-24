package ru.andrw.java.socialnw.model.auth;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by john on 10/10/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
@Entity
public class AuthToken implements Serializable {

    // Constants ------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties -----------------------------------------------------------------
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true, nullable=false)
    private String key;
    private Long value;

}
