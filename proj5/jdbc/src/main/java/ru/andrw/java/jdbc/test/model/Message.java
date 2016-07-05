package ru.andrw.java.jdbc.test.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by john on 7/5/2016.
 */
@Data
@Entity
@Table(name = "messages")
public class Message {
    private Long id;

    @GeneratedValue
    @Id
    public Long getId() {
        return id;
    }

    private String text;
    private User owner;
}
