package example.listener;

import example.dao.impl.PostgreUserDao;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by john on 8/12/2016.
 * @author andrei.belov aka john
 * based on @link github.com/Vyacheslav-Lapin/GunShop/
 */
@WebListener
public class InitListener implements ServletContextListener {

    private final String USER_DAO = "userDao";

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute(USER_DAO, new PostgreUserDao());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Shutting down!");
    }
}
