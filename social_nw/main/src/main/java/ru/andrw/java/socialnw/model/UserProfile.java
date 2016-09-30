package ru.andrw.java.socialnw.model;

import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by john on 9/25/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
@Accessors(chain = true)
public class UserProfile implements Serializable {

    // Constants ------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties -----------------------------------------------------------------

    private Long id;
    private Long userid;
    private String name;
    private String lastName;
    private Date birthDate;
    private String country;
    private String city;
    private String email;
    private String status;
    private String photoid;

    // Object overrides -----------------------------------------------------------

    /**
     * Returns the String representation of this User.
     * Not required, it just pleases reading logs.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "UserProfile{" + "id=" + id + ", name=" + name +
                ", lastName=" + lastName + ", birthDate=" + birthDate +
                ", country=" + country + ", city=" + city +
                ", email=" + email + '}';
    }
}
