package ru.andrw.java.faces.model.items;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by john on 7/15/2016.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
}
