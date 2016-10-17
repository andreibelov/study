package ru.andrw.java.socialnw.dao.impl.h2;

import java.sql.Connection;
import java.util.function.Supplier;

import ru.andrw.java.socialnw.dao.SectionDao;

/**
 * Created by john on 10/17/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
class H2SectionDao implements SectionDao {

    private Supplier<Connection> supplier;

    H2SectionDao(Supplier<Connection> connectionSupplier) {
        this.supplier = connectionSupplier;
    }

    @Override
    public Connection getConnection() {
        return supplier.get();
    }
}
