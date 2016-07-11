package ru.andrw.java.jdbc.test.control;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.ZoneId;


/**
 * Created by john on 7/5/2016.
 */
@Converter
public class SQLDateConverter implements AttributeConverter<java.util.Date, java.sql.Date> {

    @Override
    public java.sql.Date convertToDatabaseColumn(java.util.Date locDate) {
        return (locDate == null ? null : java.sql.Date.valueOf(locDate.toString()));
    }

    @Override
    public java.util.Date convertToEntityAttribute(java.sql.Date sqlDate) {
        return (sqlDate == null ? null : java.util.Date.from(sqlDate.toLocalDate()
                .atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }
}
