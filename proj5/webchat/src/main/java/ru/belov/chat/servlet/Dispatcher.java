package ru.belov.chat.servlet;

import ru.belov.chat.core.MatchItr;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.StreamSupport.stream;


/**
 * Created by john on 7/7/2016.
 * My own dispatcher
 */
@WebServlet(name = "Dispatcher", urlPatterns = {"/"})
public class Dispatcher extends javax.servlet.http.HttpServlet {

    private void doDispatch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");

        Matcher matcher = Pattern.compile("(^\\/\\p{L}{1,32}\\.jsp\\???)")
                .matcher(request.getRequestURI()
                        .substring(request.getContextPath().length()).toLowerCase());

        String to = stream(new MatchItr(matcher), false)
                .map(gs->gs[1].toLowerCase())
                .findAny().orElse("welcome.jsp");

        request.getRequestDispatcher("jsp\\"+to).forward(request,response);

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