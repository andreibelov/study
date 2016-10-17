package ru.andrw.java.socialnw.model;

import org.eclipse.persistence.annotations.Index;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.andrw.java.socialnw.model.auth.User;
import ru.andrw.java.socialnw.model.enums.Gender;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.*;

/**
 * Created by john on 9/25/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */

@Data
@Entity
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true, exclude={"status"})
public class Profile extends User implements Serializable{

    // Constants ------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties -----------------------------------------------------------------

    @Index @Column(columnDefinition = "VARCHAR (32)")
    private String firstName;
    @Index @Column(columnDefinition = "VARCHAR (32)")
    private String lastName;
    @Index
    private Date birthDate;
    @Enumerated(EnumType.STRING)
    private Gender sex;
    @Index @Column(columnDefinition = "VARCHAR (2)")
    private String country;
    @Index @Column(columnDefinition = "VARCHAR (27)")
    private String city;
    @Index
    private Date regDate;
    @Index @Column(columnDefinition = "VARCHAR (16)")
    private String phone;
    @Column(columnDefinition = "VARCHAR (64)")
    private String status;
    @Column(nullable=false, columnDefinition = "binary(16)")
    private UUID photo;

}
