package ru.andrw.java.socialnw.model;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by john on 10/5/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
@Accessors(chain = true)
public class SectionModule implements Serializable {

    // Constants -----------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Constructor ---------------------------------------------------------------

    public SectionModule(){
        this.cssFile = "empty.css";
        this.jsFile = "empty.js";
        this.jspFile = "empty.jsp";
    }


    // Properties ----------------------------------------------------------------

    private String sectionName;
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
