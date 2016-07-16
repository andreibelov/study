package ru.andrw.java.faces.model.units;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by john on 7/15/2016.
 * @author andrei.belov aka john
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Customer extends User {

    // Constants ----------------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------
    private String email;
    @OneToMany
    private List<Order> orderList;

}
