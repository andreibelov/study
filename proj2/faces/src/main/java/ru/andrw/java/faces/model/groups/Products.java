package ru.andrw.java.faces.model.groups;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.andrw.java.faces.model.items.Product;

import javax.persistence.AssociationOverride;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by john on 7/16/2016.
 */
@Data
@Entity
public class Products {

    // Constants ----------------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    @OneToMany
    private final Set<Product> products;


    public Products(){
        products = new HashSet<>();
    }
}
