package ru.andrw.java.security;

import java.io.IOException;
import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by john on 6/23/2016.
 *
 */

public class JDBCHelperClass {


    public Connection getNewConnection() throws IOException {
        String jdbcPropsFileName = "jdbc.props";
        String url = "jdbc:postgresql://localhost/javabase";
        Properties jdbcProps = new Properties();
        Connection conn = null;
        try {
            jdbcProps.load(JDBCHelperClass.class.getClassLoader().getResourceAsStream(jdbcPropsFileName));
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, jdbcProps);
            conn.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return conn;
    }


}
