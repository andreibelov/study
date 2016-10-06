package ru.andrw.java.socialnw.listener;

import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final Logger logger = LoggerFactory
            .getLogger(ScInitListener.class);
    private final String DAO_FACTORY = "daoFactory";
    private final String PROJECT_NAME = "projectName";

    static public final String TEST_GROUP = "TEST_GROUP";
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute(DAO_FACTORY, new ListDaoFactory());
        servletContext.setAttribute(PROJECT_NAME, "VAULT-TEC");
        ClassLoader ctcc = Thread.currentThread().getContextClassLoader();
        logger.info("Classload hashcode is " + ctcc.hashCode());
        logger.info("Initializing for ServletContext [" +
                servletContext.getServletContextName() + "]");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        logger.warn("Shutting down ServletContext [" +
                servletContext.getServletContextName() + "]");

        ClassLoader ctcc = Thread.currentThread().getContextClassLoader();
        logger.info("Classload hashcode is " + ctcc.hashCode());
    }
}
