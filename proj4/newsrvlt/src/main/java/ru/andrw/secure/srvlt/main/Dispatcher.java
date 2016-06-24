package ru.andrw.secure.srvlt.main;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by john on 6/24/2016.
 *
 */

@WebServlet(name = "Dispatcher", urlPatterns = {"/"})
public class Dispatcher extends javax.servlet.http.HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doMagic(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doMagic(request, response);
    }

    private void doMagic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        RequestDispatcher requestDispatcher = null;
        String path = request.getRequestURI().substring(request.getContextPath().length());
        if (path.startsWith("/static/")) {
            getServletContext().getNamedDispatcher("default").forward(request, response);
        } else if (path.startsWith("/login.jsp")){
            request.getRequestDispatcher("jsp\\login.jsp").forward(request,response);
        } else {
            request.getRequestDispatcher("jsp\\welcome.jsp").forward(request,response);
        }
    }
}