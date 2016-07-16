package ru.andrw.java.faces.model.units;

import lombok.Data;
import ru.andrw.java.faces.model.items.Pill;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

/**
 * Created by john on 7/15/2016.
 * @author john
 */
@Data
@Entity
public class DrugStore implements Serializable {

    // Constants ----------------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------

    @GeneratedValue
    @Id
    private Long id;
    private String title;
    @OneToMany
    private List<Pill> stock;
}
