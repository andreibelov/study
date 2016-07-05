package ru.andrw.java.jdbc.test.model;

import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Date;


import ru.andrw.java.jdbc.test.control.DateAdapter;



/**
 * Created by john on 7/5/2016.
 */
@Data
@Entity
@Table(name = "users")
@XmlRootElement(name="user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Serializable {

    // Constants ----------------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------

    private Long id;

    @GeneratedValue
    @Id
    public Long getId() {
        return id;
    }

    private String email;
    private String login;
    private String phone;
    private String password;
    private String firstname;
    private String lastname;

    @XmlSchemaType(name = "date")
    private Date birthday;


    // Object overrides ---------------------------------------------------------------------------

    /**
     * The user ID is unique for each User. So this should compare User by ID only.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other) {
        return (other instanceof User) && (id != null)
                ? id.equals(((User) other).id)
                : (other == this);
    }

    /**
     * The user ID is unique for each User. So User with same ID should return same hashcode.
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return (id != null)
                ? (this.getClass().hashCode() + id.hashCode())
                : super.hashCode();
    }

    /**
     * Returns the String representation of this User. Not required, it just pleases reading logs.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("User[id=%d,email=%s,firstname=%s,lastname=%s,birthdate=%s]",
                id, email, firstname, lastname, birthday);
    }

}
