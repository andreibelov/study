package ru.andrw.java.jdbc.test.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by john on 7/5/2016.
 */
@Data
@Entity
@Table(name = "messages")
public class Message {
    private Long id;
    private String text;
    private User owner;

    @GeneratedValue
    @Id
    public Long getId() {
        return id;
    }

    @ManyToOne
    public User getOwner(){
        return owner;
    }

}
