package ru.andrw.java.pharm.model.groups;

import lombok.Data;
import ru.andrw.java.pharm.model.items.Product;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by john on 7/16/2016.
 */
@Data
@Entity
public class PriceList implements Serializable {

    // Constants ----------------------------------------------------------------------------------
    @Version
    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Product product;
    private Long price;
    @OneToOne(mappedBy = "price")
    private OrderLine line;
}
