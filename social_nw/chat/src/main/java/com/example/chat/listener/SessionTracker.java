package com.example.chat.listener;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by john on 9/20/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebListener
public class SessionTracker implements ServletContextListener, HttpSessionListener {
    private final ConcurrentMap<String, HttpSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void contextInitialized(ServletContextEvent event) {
        event.getServletContext().setAttribute(getClass().getName(), this);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        sessions.put(event.getSession().getId(), event.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        sessions.remove(event.getSession().getId());
    }

    public HttpSession getSessionById(String id) {
        return sessions.get(id);
    }
}
