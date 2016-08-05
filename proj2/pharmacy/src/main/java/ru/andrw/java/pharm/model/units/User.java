package ru.andrw.java.pharm.model.units;

import lombok.Data;
import ru.andrw.java.pharm.model.groups.UserGroup;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by john on 7/15/2016.
 * @author andrei.belov aka john
 */
@Data
@Embeddable
public class User implements Serializable {

    // Constants ----------------------------------------------------------------------------------
    @Version
    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------

    @ManyToOne(cascade={CascadeType.ALL})
    private UserGroup group;
    private String login;
    private String password;

}
