package ru.andrw.java.faces.model.items;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.andrw.java.faces.model.units.DrugStore;
import ru.andrw.java.faces.model.groups.PillGroup;

import javax.persistence.*;

/**
 * Created by john on 7/15/2016.
 * @author john
 */
@Data
@Entity
@AssociationOverride(name = "product")
@EqualsAndHashCode(callSuper = true)
public class Pill extends Default {

    // Constants ----------------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------
    private String name;
    @ManyToOne
    private PillGroup type;
    @ManyToOne
    private DrugStore firm;

}
