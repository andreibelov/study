package ru.andrw.java.socialnw.dao.impl.h2;

import java.sql.Connection;
import java.util.function.Supplier;

import ru.andrw.java.socialnw.dao.PostDao;

/**
 * Created by john on 10/17/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
class H2PostDao implements PostDao {


    private Supplier<Connection> supplier;
    private final String SPLITERATOR = ".";
    private final String SCHEMA_NAME = "PUBLIC";
    private final String POST_TBL = "POST";
    private final String POST_TNAME = SCHEMA_NAME+SPLITERATOR+POST_TBL;


    H2PostDao(Supplier<Connection> supplier){
        this.supplier = supplier;
    }

    @Override
    public Connection getConnection() {
        return supplier.get();
    }

    // Private methods --------------------------------------------------------------
}
