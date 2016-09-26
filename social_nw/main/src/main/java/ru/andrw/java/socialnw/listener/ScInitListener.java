package ru.andrw.java.socialnw.listener;

import com.google.gson.Gson;
import ru.andrw.java.socialnw.dao.impl.list.ListDaoFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by john on 9/25/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebListener
public class ScInitListener implements ServletContextListener {

    private final String DAO_FACTORY = "daoFactory";
    private final String GSON = "gson";

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute(DAO_FACTORY, new ListDaoFactory());
        servletContext.setAttribute(GSON, new Gson());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Shutting down!");
    }
}
