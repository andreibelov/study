package ru.andrw.java.socialnw.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by john on 10/5/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
@Entity
@Accessors(chain = true)
public class Section implements Serializable {

    // Constants -----------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Constructor ---------------------------------------------------------------

    public Section(){
        this.cssFile = "empty.css";
        this.jsFile = "empty.js";
        this.jspFile = "empty.jsp";
    }


    // Properties ----------------------------------------------------------------

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String sectionName;
    private String pageTitle;
    private String data;
    private String jspFile;
    private String cssFile;
    private String jsFile;

    // Object overrides -----------------------------------------------------------

    /**
     * Returns the String representation of this User.
     * Not required, it just pleases reading logs.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("SectionModule[sectionName=%s,cssFile=%s,jspFile=%s,jsFile=%s]",
        sectionName,jspFile,cssFile,jsFile);
    }
}
