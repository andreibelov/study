package ru.andrw.java.pharm.model.items;

import lombok.Data;
import ru.andrw.java.pharm.model.groups.OrderLine;
import ru.andrw.java.pharm.model.units.Customer;

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
@Table(name = "ORDERS")
public class Order implements Serializable {

    // Constants ----------------------------------------------------------------------------------
    @Version
    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
    private UUID uuid;
    @OneToMany(mappedBy = "order")
    private Set<OrderLine> cart;
    private double totalPrice;

    @ManyToOne
    private Customer customer;

}
