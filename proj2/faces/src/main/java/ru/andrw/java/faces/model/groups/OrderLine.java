package ru.andrw.java.faces.model.groups;

import lombok.Data;
import ru.andrw.java.faces.model.items.Product;
import ru.andrw.java.faces.model.units.Order;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 * Created by john on 7/15/2016.
 * @author andrei.belov aka john
 */
@Data
@Entity
public class OrderLine implements Serializable {

    // Constants ----------------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------
    @GeneratedValue
    @Id
    private Long id;
    @OneToOne
    private Order order;
    @OneToOne
    public Product product;
    public int quantity;
    public double price;
}
