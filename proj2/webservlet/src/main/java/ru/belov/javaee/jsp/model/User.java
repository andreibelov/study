package ru.belov.javaee.jsp.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by john on 7/22/2016.
 */
@Data
@Entity
@Accessors(chain = true)
@XmlRootElement(name="user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Serializable {

    // Constants ----------------------------------------------------------------------------------

    private static final long serialVersionUID = 3637310585741732936L;

    // Properties ---------------------------------------------------------------------------------

    protected Long id;
    protected String login;
    protected String pass;
    protected String email;

}
