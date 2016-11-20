package ru.andrw.java.socialnw.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by john on 10/25/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class Validator {

    private static final Logger logger = LoggerFactory
            .getLogger(Validator.class);

    private static final String usernamePattern = "^[a-z0-9._]+$";
    private static final String emailPattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final String birthDatePattern = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    private static final String countryPattern = "^[A-Z]{2}$";
    private static final String cityPattern = "^\\p{L}+$";
    private static final String namePattern = "^\\p{L}+$";
    private static final String phonePattern = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";
    private static final String uuidPattern = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
    private static final String genderPattern = "^[0-3]$";
    private static final String passwordPattern = "^[A-Za-z0-9._](?=\\S+$).{6,}$";

    public static Optional<Map<String,String>> getValidParams(HttpServletRequest req){
        Map<String,String> params = new HashMap<>();
        List<Boolean> booleans = new ArrayList<>();

        params.put("email",req.getParameter("email"));
        params.put("login",req.getParameter("login"));
        params.put("birthDate",req.getParameter("birthDate"));
        params.put("country",req.getParameter("country"));
        params.put("city",req.getParameter("city"));
        params.put("firstName",req.getParameter("firstName"));
        params.put("lastName",req.getParameter("lastName"));
        params.put("mobile",req.getParameter("mobile"));
        params.put("photo",req.getParameter("photo"));
        params.put("gender",req.getParameter("gender"));

        booleans.add(params.get("login").matches(usernamePattern));
        booleans.add(params.get("email").matches(emailPattern));
        booleans.add(params.get("birthDate").matches(birthDatePattern));
        booleans.add(params.get("country").matches(countryPattern));
        booleans.add(params.get("city").matches(cityPattern));
        booleans.add(params.get("firstName").matches(namePattern));
        booleans.add(params.get("lastName").matches(namePattern));
        booleans.add(params.get("mobile").matches(phonePattern));
        booleans.add(params.get("photo").matches(uuidPattern));
        booleans.add(params.get("gender").matches(genderPattern));

        for (boolean b: booleans) {
            logger.debug(":"+b);
        }

        return (params.values().stream().filter(Objects::isNull).findAny().isPresent()) ||
                (booleans.contains(false))?
           Optional.empty():Optional.ofNullable(params);

    }
}
