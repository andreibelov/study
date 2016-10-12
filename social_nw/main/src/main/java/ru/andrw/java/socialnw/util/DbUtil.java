package ru.andrw.java.socialnw.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.function.Supplier;

/**
 * Created by john on 9/25/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class DbUtil implements Supplier<Connection> {

    private static Connection connection = null;
    private final Logger logger = LoggerFactory.getLogger("ru.andrw.java.socialnw.util.DbUtil");
    public Connection getConnection() {
        return this.get();
    }

    @Override
    public Connection get() {
        if (connection != null)
            return connection;
        else {
            try {
                Properties prop = new Properties();
                InputStream inputStream = DbUtil.class.getClassLoader().getResourceAsStream("db.properties");
                prop.load(inputStream);
                Class.forName(prop.getProperty("driver"));
                String url = prop.getProperty("url");
                String user = prop.getProperty("user");
                String password = prop.getProperty("password");
                connection = DriverManager.getConnection(url, user, password);
            } catch (ClassNotFoundException | IOException | SQLException e) {
                logger.error("Something went wrong :( ",e);
            }
            return connection;
        }
    }
}
