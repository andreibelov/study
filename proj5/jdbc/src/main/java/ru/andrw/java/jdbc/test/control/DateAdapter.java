package ru.andrw.java.jdbc.test.control;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.sql.Date;

/**
 * Created by john on 7/5/2016.
 */
public class DateAdapter extends XmlAdapter<String, Date> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public String marshal(Date v) throws Exception {
            return dateFormat.format(v);
    }

    @Override
    public Date unmarshal(String v) throws Exception {
            return Date.valueOf(v);
    }

}
