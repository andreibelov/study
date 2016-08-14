package ru.belov.study.proj5.model.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by john on 7/22/2016.
 * author andrei.belov aka john
 */
@Data
@Entity
@Embeddable
@Accessors(chain = true)
@XmlRootElement(name="user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Serializable {

    // Constants ----------------------------------------------------------------------------------
    @Version
    private static final long serialVersionUID = 0L;

    // Properties ---------------------------------------------------------------------------------
    @GeneratedValue
    @Id
    private Long id;
    protected String login;
    protected String password;
    protected String email;

}