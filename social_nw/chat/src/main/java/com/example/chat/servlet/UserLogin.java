package com.example.chat.servlet;

import com.example.chat.core.OptionalConsumer;
import com.example.chat.dao.DaoException;
import com.example.chat.dao.DaoFactory;
import com.example.chat.dao.UserDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by john on 8/14/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet(name = "UserLogin", urlPatterns = {"/login"})
public class UserLogin extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext sc = config.getServletContext();
        DaoFactory daoFactory = (DaoFactory) sc.getAttribute("daoFactory");
        userDao = daoFactory.getUserDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String login = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            OptionalConsumer.of(userDao.find(login,password))
                    .ifPresent(usr -> {
                        request.getSession().setAttribute("user", usr);// Put user in session.
                        Cookie userName = new Cookie("user", usr.getFirstName());
                        response.addCookie(userName);
                        try {
                            response.sendRedirect(request.getContextPath()+"/chat.jsp");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }})
                    .ifNotPresent(() -> {
                        request.setAttribute("error", "Unknown login, try again"); // Set error msg for ${error}
                        try {
                            request.getRequestDispatcher("/login.jsp").forward(request, response); // Go back to login page.);
                        } catch (ServletException | IOException e1) {
                            e1.printStackTrace();
                        }
                    });
        } catch (DaoException e) {
            e.printStackTrace();
        }

    }

}
