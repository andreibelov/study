package ru.andrw.java.pharm.model.groups;

import lombok.Data;
import ru.andrw.java.pharm.model.items.Product;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by john on 7/16/2016.
 */
@Data
@Entity
public class ProdGroup implements Serializable {

    // Constants ----------------------------------------------------------------------------------
    @Version
    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------
    @GeneratedValue
    @Id
    private Long id;
    @OneToMany(mappedBy = "group")
    private List<Product> list;
}
