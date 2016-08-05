package ru.andrw.java.pharm.model.items;

import lombok.Data;
import ru.andrw.java.pharm.model.groups.PriceList;
import ru.andrw.java.pharm.model.groups.ProdGroup;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by john on 7/15/2016.
 */
@Data
@Entity
public class Product implements Serializable {

    // Constants ----------------------------------------------------------------------------------
    @Version
    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------
    @GeneratedValue
    @Id
    private Long id;
    private String title;
    private String description;
    @ManyToOne
    private ProdGroup group;
    @OneToOne(mappedBy = "product")
    private PriceList price;

}
