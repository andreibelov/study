package com.example.chat.servlet;

import com.example.chat.dao.DaoException;
import com.example.chat.dao.DaoFactory;
import com.example.chat.dao.UserDao;
import com.example.chat.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;


/**
 * Created by john on 8/14/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet(name = "UserLogin", urlPatterns = {"/login"})
public class UserLogin extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger(UserLogin.class);
    private UserDao userDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext sc = config.getServletContext();
        DaoFactory daoFactory = (DaoFactory) sc.getAttribute("daoFactory");
        userDao = daoFactory.getUserDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        Optional<User> user = empty();
        Optional<String> o_email = ofNullable(request.getParameter("email"));
        Optional<String> o_password = ofNullable(request.getParameter("password"));

        if(o_email.isPresent() && o_password.isPresent()) {
            try {
                user = userDao.findUser(o_email.get(), o_password.get());
                if (user.isPresent()) {
                    request.getSession().setAttribute("user", user.get()); // Put user in a session.
                    Cookie userName = new Cookie("user", user.get().getLogin());
                    response.addCookie(userName);
                    response.sendRedirect(request.getContextPath() + "/"); // Go to some start page.
                } else {
                    request.setAttribute("error", "Unknown login, try again"); // Set error msg for ${error}
                    request.getRequestDispatcher("/login").forward(request, response); // Go back to login page.
                }
            } catch (DaoException e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.error("Empty fields found");
            request.setAttribute("error", "Empty fields found"); // Set error msg for ${error}
            request.getRequestDispatcher("/login").forward(request, response); // Go back to login page.
        }
    }

}
