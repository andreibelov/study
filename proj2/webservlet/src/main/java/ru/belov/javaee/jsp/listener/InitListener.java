package ru.belov.javaee.jsp.listener;

import com.google.gson.Gson;
import ru.belov.javaee.jsp.dao.impl.MessageDAOList;
import ru.belov.javaee.jsp.dao.impl.UserDAOList;
import ru.belov.javaee.jsp.service.MsgQueue;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by john on 7/22/2016.
 * @author andrei.belov aka john
 * based on @link github.com/Vyacheslav-Lapin/GunShop/
 */
@WebListener
public class InitListener implements ServletContextListener {
    private final String GSON = "gson";
    private final String USER_DAO = "userDao";
    private final String MSG_QUEUE = "msgQueue";
    private final String CHAT_DAO = "chatdao";

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute(GSON, new Gson());
        servletContext.setAttribute(MSG_QUEUE, new MsgQueue());
//        servletContext.setAttribute(USER_DAO, UserDAOList.getInstance());
        servletContext.setAttribute(CHAT_DAO, MessageDAOList.getInstance());

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Shutting down!");
    }
}
