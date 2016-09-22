package ru.andrw.java.jsonchat.listener;

import ru.andrw.java.jsonchat.dao.impl.list.ListDaoFactory;
import ru.andrw.java.jsonchat.dao.impl.postgres.PgDaoFactory;
import ru.andrw.java.jsonchat.util.DbUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by john on 7/24/2016.
 * @author andrei.belov aka john
 * based on @link github.com/Vyacheslav-Lapin/GunShop/
 */
@WebListener
public class ServletInitListener implements ServletContextListener {

    private final String DAO_FACTORY = "daoFactory";

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute(DAO_FACTORY, new ListDaoFactory());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Shutting down!");
    }
}
