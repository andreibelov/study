package ru.andrw.java.faces.model.units;

import lombok.Data;
import ru.andrw.java.faces.model.groups.OrderLine;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

/**
 * Created by john on 7/15/2016.
 * @author andrei.belov aka john
 */
@Data
@Entity
public class Order implements Serializable {

    // Constants ----------------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------
    @GeneratedValue
    @Id
    private Long id;
    private UUID uuid;
    @OneToMany(cascade={CascadeType.ALL})
    private Set<OrderLine> cart;
    private double totalPrice;
    @OneToOne
    private Customer customer;

}
