package ru.andrw.java.pharm.model.groups;

import lombok.Data;
import ru.andrw.java.pharm.model.items.Order;
import ru.andrw.java.pharm.model.items.Product;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by john on 7/15/2016.
 * @author andrei.belov aka john
 */
@Data
@Entity
public class OrderLine implements Serializable {

    // Constants ----------------------------------------------------------------------------------
    @Version
    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Order order;
    @OneToOne
    private PriceList price;

    public int quantity;
    public double cost;

}
