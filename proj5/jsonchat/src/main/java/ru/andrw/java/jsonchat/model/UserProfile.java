package ru.andrw.java.jsonchat.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by john on 9/20/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
@Accessors(chain = true)
public class UserProfile implements Serializable {

    // Constants ----------------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------

    private UUID uuid;
    private Long userid;
    private String link;
    private String avatar;
    private String name;
    private String surname;
    private String phone;
    private Date dob;

    // Object overrides ---------------------------------------------------------------------------

    /**
     * Returns the String representation of this User. Not required, it just pleases reading logs.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("User[userid=%d, name: %s, surname: %s, phone: %s, birthday: %tA %<tB  %<te,  %<tY  %n]",
                userid, name,surname,phone,dob);
    }

}
