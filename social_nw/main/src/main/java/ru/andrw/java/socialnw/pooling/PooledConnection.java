package ru.andrw.java.socialnw.pooling;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Predicate;

/**
 * Created by john on 10/10/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@SuppressWarnings("WeakerAccess")
public interface PooledConnection extends ConnectionWrapper {

    static PooledConnection create(Connection connection,
                                   Predicate<PooledConnection> freeMethod) throws SQLException {

        connection.setAutoCommit(true);

        return new PooledConnection() {
            @Override
            public Connection toSrc() {
                return connection;
            }

            @Override
            public void close() throws SQLException {
                Connection connection = toSrc();
                if (connection.isClosed()) throw new SQLException("Attempting to close closed connection.");
                if (connection.isReadOnly()) connection.setReadOnly(false);
                if (!freeMethod.test(this)) throw new SQLException("Error allocating connection in the pool.");
            }
        };
    }
}
