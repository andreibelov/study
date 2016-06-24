package ru.belov.java.proj4.webservlet.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Created by john on 6/22/2016.
 *
 */

public class DBConn {
    public Connection getNewConnection() throws IOException {
        String jdbcPropsFileName = "jdbc.props";
        String url = "jdbc:postgresql://localhost/javabase";
        Properties jdbcProps = new Properties();
        Connection conn = null;
        try {
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
