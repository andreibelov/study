package ru.andrw.java.pharm.model.groups;

import lombok.Data;
import ru.andrw.java.pharm.model.units.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by john on 7/15/2016.
 * @author john
 */
@Data
@Entity
public class UserGroup implements Serializable {

    // Constants ----------------------------------------------------------------------------------
    @Version
    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String title;
    @ElementCollection
    private Set<User> list;
}
