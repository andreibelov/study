package com.example.chat.servlet;

import com.example.chat.dao.DaoException;
import com.example.chat.dao.DaoFactory;
import com.example.chat.dao.UserDao;
import com.example.chat.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * Created by john on 8/12/2016.
 * based on @link https://danielniko.wordpress.com/2012/04/17/simple-crud-using-jsp-servlet-and-mysql/
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet(name = "UserController", urlPatterns = {"/users"})
public class UserController extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static String INSERT_OR_EDIT = "/user.jsp";
    private static String LIST_USER = "/listUser.jsp";
    private UserDao userDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext sc = config.getServletContext();
        DaoFactory pgDaoFactory = (DaoFactory) sc.getAttribute("daoFactory");
        userDao = pgDaoFactory.getUserDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward="";
        String action = request.getParameter("action");
        try {
            if (action.equalsIgnoreCase("delete")) {
                Long userId = Long.parseLong(request.getParameter("userId"));
                userDao.deleteUser(userId);
                forward = LIST_USER;
                request.setAttribute("users", userDao.getAllUsers());
            } else if (action.equalsIgnoreCase("edit")) {
                forward = INSERT_OR_EDIT;
                Long userId = Long.parseLong(request.getParameter("userId"));
                Optional<User> o_user = userDao.getUserById(userId);
                if (o_user.isPresent())request.setAttribute("user", o_user.get());
            } else if (action.equalsIgnoreCase("listUser")) {
                forward = LIST_USER;
                request.setAttribute("users", userDao.getAllUsers());
            } else {
                forward = INSERT_OR_EDIT;
            }
        } catch (DaoException ex){
            logger.error(ex.getMessage(),ex);
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
