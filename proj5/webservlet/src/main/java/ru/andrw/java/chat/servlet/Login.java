package ru.andrw.java.chat.servlet;

import ru.andrw.java.chat.dao.exeptions.DAOException;
import ru.andrw.java.chat.dao.ifaces.UserDAO;
import ru.andrw.java.chat.dao.list.UserDAOList;
import ru.andrw.java.chat.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by john on 7/1/2016.
 * This class puts user into session in case of correct login password pair provided.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class Login extends javax.servlet.http.HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        UserDAO userDAO = UserDAOList.getInstance();
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        User user = null;
        try {
            user = userDAO.find(login, password);
        } catch (DAOException e) {
            e.printStackTrace();
        }


        if (user != null) {
            request.getSession().setAttribute("user", user); // Put user in session.
            Cookie userName = new Cookie("user", user.getFirstname());
            response.addCookie(userName);
            response.sendRedirect(request.getContextPath() +"/welcome.jsp"); // Go to some start page.
        } else {
            request.setAttribute("error", "Unknown login, try again"); // Set error msg for ${error}
            request.getRequestDispatcher("/login.jsp").forward(request, response); // Go back to login page.
        }
    }
}
