package ru.andrw.java.jsonchat.servlet;

import ru.andrw.java.jsonchat.core.MatchItr;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.StreamSupport.stream;

/**
 * Created by john on 7/3/2016.
 * My own dispatcher
 */
@WebServlet(name = "Dispatcher", urlPatterns = {"/"})
public class Dispatcher extends javax.servlet.http.HttpServlet {

    private void doDispatch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String uri = request.getRequestURI().substring(request.getContextPath().length());
        Matcher matcher = Pattern.compile("^/(\\p{L}{1,32}\\.jsp).*")
                .matcher(uri.toLowerCase());

        String to = stream(new MatchItr(matcher), false)
                .map(gs->gs[1].toLowerCase()) // TODO lookup for jsp's
                .findAny().orElse("/main.jsp");


        if (uri.startsWith("/static/")) {
            getServletContext().getNamedDispatcher("default").forward(request, response);
        } else if (uri.startsWith("/jsp/")){
            response.setContentType("text/html;charset=utf-8");
            getServletContext().getNamedDispatcher("jsp").forward(request, response);
        } else {
            response.setContentType("text/html;charset=utf-8");
            request.getRequestDispatcher("/jsp/"+to).forward(request,response);
        }

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