package ru.andrw.java.socialnw.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.andrw.java.socialnw.dao.DaoFactory;
import ru.andrw.java.socialnw.dao.TokensDao;
import ru.andrw.java.socialnw.dao.UserDao;
import ru.andrw.java.socialnw.service.LoginService;

/**
 * Created by john on 10/10/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet(name = "PostsServlet")
public class Posts extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger(Posts.class);


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext sc = config.getServletContext();
        DaoFactory daoFactory = (DaoFactory) sc.getAttribute("daoFactory");
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {



    }
}
