package ru.andrw.java.pharm.model.units;

import lombok.Data;
import ru.andrw.java.pharm.model.groups.PriceList;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by john on 7/15/2016.
 * @author john
 */
@Data
@Entity
public class DrugStore implements Serializable {

    // Constants ----------------------------------------------------------------------------------
    @Version
    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------

    @GeneratedValue
    @Id
    private Long id;
    private String title;
    @ManyToOne
    private PriceList priceList;
}
