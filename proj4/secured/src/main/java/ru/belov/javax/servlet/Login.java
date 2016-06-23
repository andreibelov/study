package ru.belov.javax.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = UserDAO.find(username, password);

        if (user != null) {
            request.getSession().setAttribute("user", user); // Put user in session.
            response.sendRedirect("/secured/home.jsp"); // Go to some start page.
        } else {
            request.setAttribute("error", "Unknown login, try again"); // Set error msg for ${error}
            request.getRequestDispatcher("/login.jsp").forward(request, response); // Go back to login page.
        }
    }

}
