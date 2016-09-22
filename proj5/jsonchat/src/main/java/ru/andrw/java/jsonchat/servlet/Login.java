package ru.andrw.java.jsonchat.servlet;

import com.google.gson.Gson;
import ru.andrw.java.jsonchat.dao.DaoException;
import ru.andrw.java.jsonchat.dao.DaoFactory;
import ru.andrw.java.jsonchat.dao.UserDao;
import ru.andrw.java.jsonchat.model.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static java.util.Optional.empty;

/**
 * Created by john on 7/3/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class Login extends javax.servlet.http.HttpServlet {

    private UserDao userDao;
    private Gson gson;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext sc = config.getServletContext();
        DaoFactory daoFactory =   (DaoFactory) sc.getAttribute("daoFactory");

        userDao = daoFactory.getUserDao();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        Optional<User> user = empty();
        try {
            user = userDao.findUser(login, password);
        } catch (DaoException e) {
            e.printStackTrace();
        }

        if (user.isPresent()) {
            request.getSession().setAttribute("user", user.get()); // Put user in session.
            Cookie userName = new Cookie("user", user.get().getLogin());
            response.addCookie(userName);
            response.sendRedirect(request.getContextPath() +"/index.jsp"); // Go to some start page.
        } else {
            request.setAttribute("error", "Unknown login, try again"); // Set error msg for ${error}
            request.getRequestDispatcher("/login.jsp").forward(request, response); // Go back to login page.
        }
    }

}
