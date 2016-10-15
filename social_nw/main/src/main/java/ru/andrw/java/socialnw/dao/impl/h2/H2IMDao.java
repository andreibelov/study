package ru.andrw.java.socialnw.dao.impl.h2;

import java.sql.Connection;
import java.util.function.Supplier;

import ru.andrw.java.socialnw.dao.IMDao;

/**
 * Created by john on 10/15/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
class H2IMDao implements IMDao {

    private final Supplier<Connection> supplier;

    H2IMDao(Supplier<Connection> supplier) {
        this.supplier = supplier;
    }

    

    @Override
    public Connection getConnection() {
        return supplier.get();
    }

    // Private methods -------------------------------------------------------------------

}
