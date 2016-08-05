package ru.andrw.java.pharm.model.units;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Created by john on 7/16/2016.
 */
@Data
@Entity
public class Account implements Serializable {

    // Constants ----------------------------------------------------------------------------------
    @Version
    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------
    @GeneratedValue
    @Id
    private Long id;
    @Embedded
    private User user;
//    @OneToOne
//    private Customer customer;
}
