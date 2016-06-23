package ru.belov.javax.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by john on 6/23/2016.
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
        String path = request.getRequestURI().substring(request.getContextPath().length());
        if (path.startsWith("/static/")) {
            getServletContext().getNamedDispatcher("default").forward(request, response);
        } else {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp\\index.jsp");
            requestDispatcher.forward(request,response);
        }
    }
}
