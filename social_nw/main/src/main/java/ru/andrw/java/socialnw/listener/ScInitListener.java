package ru.andrw.java.socialnw.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;

import ru.andrw.java.socialnw.dao.DaoFactory;
import ru.andrw.java.socialnw.dao.impl.h2.H2DaoFactory;
import ru.andrw.java.socialnw.dao.impl.list.ListDaoFactory;
import ru.andrw.java.socialnw.model.enums.Countries;
import ru.andrw.java.socialnw.model.enums.Gender;
import ru.andrw.java.socialnw.pooling.ConnectionPool;
import ru.andrw.java.socialnw.util.X509Signer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by john on 9/25/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@SuppressWarnings("FieldCanBeLocal")
@WebListener
public class ScInitListener implements ServletContextListener {

    private final Logger logger = LoggerFactory
            .getLogger(ScInitListener.class);


    private static final String RESOURCES_FILE_PATH = "/WEB-INF/classes/";
    private static final String DB_PROPERTIES_FILE_NAME = "h2.db.properties";
    private static final String DB_DROP_FILE_NAME = "H2drop.ddl";
    private static final String DB_CREATE_FILE_NAME = "H2create.ddl";
    private static final String DB_PREPARE_FILE_NAME = "h2.sql";

    private final String DAO_FACTORY = "daoFactory";
    private final String PROJECT_NAME = "projectName";

    private static ConnectionPool connectionPool;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext servletContext = sce.getServletContext();

        final String dbPropertiesFilePath = servletContext
                .getRealPath(RESOURCES_FILE_PATH + DB_PROPERTIES_FILE_NAME);
        final String dbDropFilePath = servletContext
                .getRealPath(RESOURCES_FILE_PATH + DB_DROP_FILE_NAME);
        final String dbCreateFilePath = servletContext
                .getRealPath(RESOURCES_FILE_PATH + DB_CREATE_FILE_NAME);
        final String dbPrepareFilePath = servletContext
                .getRealPath(RESOURCES_FILE_PATH + DB_PREPARE_FILE_NAME);

        try {
            connectionPool = ConnectionPool
                    .create(dbPropertiesFilePath)
                    .executeScript(dbDropFilePath)
                    .executeScript(dbCreateFilePath)
                    .executeScript(dbPrepareFilePath);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }



        final DaoFactory daoFactory = new H2DaoFactory(connectionPool);

        servletContext.setAttribute(DAO_FACTORY, daoFactory);
        servletContext.setAttribute(PROJECT_NAME, "VAULT-TEC");

        servletContext.setAttribute("countryCodes", Countries.values());
        servletContext.setAttribute("genders", Gender.values());

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
        try {
            connectionPool.close();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        ClassLoader ctcc = Thread.currentThread().getContextClassLoader();
        logger.info("Classload hashcode is " + ctcc.hashCode());
    }


}
