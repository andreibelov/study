package ru.andrw.java.socialnw.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.andrw.java.socialnw.dao.DaoFactory;
import ru.andrw.java.socialnw.dao.TokensDao;
import ru.andrw.java.socialnw.dao.UserDao;
import ru.andrw.java.socialnw.service.LoginService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by john on 9/25/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class Login extends HttpServlet {


    private final Logger logger = LoggerFactory.getLogger("ru.andrw.java.socialnw.servlet.Login");

    private UserDao userDao;
    private TokensDao tokensDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext sc = config.getServletContext();
        DaoFactory daoFactory =   (DaoFactory) sc.getAttribute("daoFactory");
        LoginService.setUserDao(daoFactory.getUserDao());
        LoginService.setProfileDao(daoFactory.getProfileDao());
        LoginService.setTokensDao(daoFactory.getTokensDao());
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {


        if (request.getSession().getAttribute("user") != null)
            response.sendRedirect(request.getContextPath() +"/home"); // Go to some start page.
        else LoginService.doGetAction(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null)
            response.sendRedirect(request.getContextPath() +"/home"); // Go to some start page.
        else LoginService.doPostAction(request,response);
    }
}