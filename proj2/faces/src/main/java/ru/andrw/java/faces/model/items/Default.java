package ru.andrw.java.faces.model.items;

import ru.andrw.java.faces.model.items.Product;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Created by john on 7/16/2016.
 */
@MappedSuperclass
public abstract class Default extends Product implements Serializable{

    // Constants ----------------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long PRODUCT_ID;
    @ManyToOne
    public Product product;

}
