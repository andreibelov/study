package ru.andrw.java.socialnw.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

/**
 * Created by john on 10/11/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
@Entity
public class Relation implements Serializable {

    // Constants -----------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ----------------------------------------------------------------

    @Id
    private String id;
    private Long idRequester;
    private Long idRequestee;
    private Integer status;
}
