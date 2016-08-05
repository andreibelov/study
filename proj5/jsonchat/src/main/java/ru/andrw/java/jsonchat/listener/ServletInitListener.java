package ru.andrw.java.jsonchat.listener;

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

    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Shutting down!");
    }
}
