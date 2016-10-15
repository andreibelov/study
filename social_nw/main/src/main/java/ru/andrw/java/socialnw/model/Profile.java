package ru.andrw.java.socialnw.model;

import org.eclipse.persistence.annotations.Customizer;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.andrw.java.socialnw.model.auth.User;
import ru.andrw.java.socialnw.model.enums.Gender;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import static javax.persistence.ConstraintMode.PROVIDER_DEFAULT;

/**
 * Created by john on 9/25/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */

@Data
@Entity
@Accessors(chain = true)
@ToString(callSuper=true, includeFieldNames=true)
@EqualsAndHashCode(callSuper = true, exclude={"status"})
public class Profile extends User implements Serializable{

    // Constants ------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties -----------------------------------------------------------------

    private String firstName;
    private String lastName;
    private Date birthDate;
    @Enumerated(EnumType.STRING)
    private Gender sex;
    private String country;
    private String city;
    private Timestamp regDate;
    private String phone;
    private String status;
    private UUID photo;

}
