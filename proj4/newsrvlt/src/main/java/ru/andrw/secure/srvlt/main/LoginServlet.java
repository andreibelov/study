package ru.andrw.secure.srvlt.main;

import ru.andrw.secure.srvlt.dao.UserDAO;
import ru.andrw.secure.srvlt.dao.UserDAOList;
import ru.andrw.secure.srvlt.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by john on 6/24/2016.
 *
 */

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        UserDAO userDAO = UserDAOList.getInstance();
        String username = request.getParameter("email");
        String password = request.getParameter("password");
        User user = userDAO.find(username, password);
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