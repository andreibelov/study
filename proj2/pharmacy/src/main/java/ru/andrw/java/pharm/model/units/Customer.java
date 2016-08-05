package ru.andrw.java.pharm.model.units;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.andrw.java.pharm.model.items.Order;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

/**
 * Created by john on 7/15/2016.
 * @author andrei.belov aka john
 */
@Data
@Entity
public class Customer implements Serializable{

    // Constants ----------------------------------------------------------------------------------
    @Version
    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------
    @GeneratedValue
    @Id
    private Long id;
    @Embedded
    private User user;
//    @Id
//    private UUID uuid;
    @Column(unique=true)
    private String email;
    @OneToMany(mappedBy="customer",cascade={CascadeType.ALL})
    private Set<Order> orderList;

}
