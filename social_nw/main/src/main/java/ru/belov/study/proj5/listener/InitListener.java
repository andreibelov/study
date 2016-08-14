package ru.belov.study.proj5.listener;

import com.google.gson.Gson;
import ru.belov.study.proj5.dao.functions.ExceptionalSupplier;
import ru.belov.study.proj5.dao.impl.ChatDaoJdbc;
import ru.belov.study.proj5.dao.impl.UserDaoJdbc;
import ru.belov.study.proj5.service.ChatQueue;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.function.Supplier;

/**
 * Created by john on 7/22/2016.
 * @author andrei.belov aka john
 * based on @link github.com/Vyacheslav-Lapin/GunShop/
 */
@WebListener
public class InitListener implements ServletContextListener {

    @Resource(name = "jdbc/postgresql")
    private DataSource dataSource;

    private final String GSON = "gson";
    private final String USER_DAO = "userDao";
    private final String MSG_QUEUE = "msgQueue";
    private final String MESSAGE_DAO = "chatdao";


    @Override
    public void contextInitialized(ServletContextEvent sce) {

        Supplier<Connection> connectionSupplier =
                ExceptionalSupplier.toUncheckedSupplier(dataSource::getConnection);

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute(GSON, new Gson());
        servletContext.setAttribute(MSG_QUEUE, new ChatQueue());
        servletContext.setAttribute(USER_DAO, new UserDaoJdbc(connectionSupplier));
        servletContext.setAttribute(MESSAGE_DAO, new ChatDaoJdbc(connectionSupplier));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Shutting down!");
    }
}
