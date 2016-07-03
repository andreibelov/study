package ru.andrw.java.jsonchat.servlet;

import ru.andrw.java.jsonchat.dao.DAO;
import ru.andrw.java.jsonchat.dao.DAOException;
import ru.andrw.java.jsonchat.dao.DaoFactory;
import ru.andrw.java.jsonchat.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by john on 7/3/2016.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class Login extends javax.servlet.http.HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        DAO chat;
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        User user = null;
        try {
            chat = DaoFactory.getDao();
            user = chat.findUser(login, password);
        } catch (DAOException e) {
            e.printStackTrace();
        }

        if (user != null) {
            request.getSession().setAttribute("user", user); // Put user in session.
            Cookie userName = new Cookie("user", user.getLogin());
            response.addCookie(userName);
            response.sendRedirect(request.getContextPath() +"/welcome.jsp"); // Go to some start page.
        } else {
            request.setAttribute("error", "Unknown login, try again"); // Set error msg for ${error}
            request.getRequestDispatcher("/login.jsp").forward(request, response); // Go back to login page.
        }
    }

}
