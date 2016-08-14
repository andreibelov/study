package ru.belov.study.proj5.servlet;


import org.apache.jasper.servlet.JspServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by john on 7/7/2016.
 * My own dispatcher
 */
@WebServlet(name = "Dispatcher", urlPatterns = {"/jsp"})
public class Dispatcher extends javax.servlet.http.HttpServlet {

    private void doDispatch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI().substring(request.getContextPath().length()).toLowerCase();
        if (uri.startsWith("/home.jsp")){
            request.setAttribute("title", "TheWALL");
            request.setAttribute("page", "wall.jsp");
            request.setAttribute("css", "wall.css");
            request.setAttribute("javascript", "wall.js");
        }
        else if (uri.startsWith("/friends/")){
            request.setAttribute("title", "FriendList");
            request.setAttribute("page", "friends.jsp");
            request.setAttribute("css", "friends.css");
            request.setAttribute("javascript", "friends.js");
        }
        else if (uri.startsWith("/chat.jsp")) {
            request.setAttribute("page", "chat.jsp");
            request.setAttribute("css", "chat.css");
            request.setAttribute("javascript", "chat.js");
        }
        request.getRequestDispatcher("/jsp/main.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doDispatch(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doDispatch(request, response);
    }

}