package ru.andrw.java.jsonchat.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by john on 7/3/2016.
 * My own dispatcher
 */
@WebServlet(name = "Dispatcher", urlPatterns = {"/"})
public class Dispatcher extends javax.servlet.http.HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doDispatch(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doDispatch(request, response);
    }

    private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        Matcher matcher = Pattern.compile("(^\\/\\p{L}{1,32}\\.jsp\\???)")
                .matcher(request.getRequestURI().substring(request.getContextPath().length()).toLowerCase());

        String forwardPath;

        // if we find a match, get the group
        if (matcher.find())
            switch (matcher.group(1)) {
                case "/login.jsp":
                    forwardPath = "jsp\\login.jsp";
                    break;
                case "/logout.jsp":
                    forwardPath = "jsp\\logout.jsp";
                    break;
                case "/chat.jsp":
                    forwardPath = "jsp\\chat.jsp";
                    break;
                case "":
                default:
                    forwardPath = "jsp\\welcome.jsp";
                    break;
            }
        else forwardPath = "jsp\\welcome.jsp";

        forwardTo(request,response,forwardPath);
    }

    private void forwardTo(HttpServletRequest request, HttpServletResponse response, String to)
            throws ServletException, IOException {
        request.getRequestDispatcher(to).forward(request,response);
    }

}