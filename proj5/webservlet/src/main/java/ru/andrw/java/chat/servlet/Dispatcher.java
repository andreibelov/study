package ru.andrw.java.chat.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by john on 7/1/2016.
 * this servlet serves clients requests and redirects them depending on requested URI
 */
@WebServlet(name = "Dispatcher", urlPatterns = {"/"})
public class Dispatcher extends javax.servlet.http.HttpServlet {

    private HttpServletRequest in;
    private HttpServletResponse out;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doDispatch(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doDispatch(request, response);
    }

    private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        in = request; out = response;
        response.setContentType("text/html;charset=utf-8");
        Matcher matcher = Pattern.compile("^\\/.{1,6}\\.jsp")
                .matcher(request.getRequestURI().substring(request.getContextPath().length()).toLowerCase());
        // if we find a match, get the group
        if (matcher.find())
            switch (matcher.group(1)) {
                case "/login.jsp":
                    forwardTo("jsp\\login.jsp");
                    break;
                case "/logout.jsp":
                    forwardTo("jsp\\logout.jsp");
                    break;
                case "/chat.jsp":
                    forwardTo("jsp\\chat.jsp");
                    break;
                case "":
                default:
                    break;
            }
        else forwardTo("jsp\\welcome.jsp");
    }

    private void forwardTo(HttpServletRequest request, HttpServletResponse response, String to) throws ServletException, IOException {
        request.getRequestDispatcher(to).forward(request,response);
    }

    private void forwardTo(String to) throws ServletException, IOException {
        forwardTo(in,out,to);
    }

}
