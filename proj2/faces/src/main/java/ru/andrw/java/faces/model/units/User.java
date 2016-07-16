package ru.andrw.java.faces.model.units;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by john on 7/15/2016.
 * @author andrei.belov aka john
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String login;
    private String password;
}
