package com.example.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by john on 8/14/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet(urlPatterns = "/*", displayName = "Dispatcher")
public class Dispatcher extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        performTask(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        performTask(request,response);
    }

    private void performTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.print("Some event\n");

        ServletContext context = request.getServletContext();

        RequestDispatcher view = context.getRequestDispatcher(request.getAttribute("javax.servlet.include.path_info").toString());
        view.

        //throw new ServletException("Some Error Occurred");

    }

}
