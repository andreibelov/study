package example.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by john on 8/12/2016.
 * Represents User object model
 */
@Data
@Accessors(chain = true)
public class User implements Serializable {

    // Constants ----------------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------

    private int userid;
    private String firstName;
    private String lastName;
    private Date dob;
    private String email;

}
