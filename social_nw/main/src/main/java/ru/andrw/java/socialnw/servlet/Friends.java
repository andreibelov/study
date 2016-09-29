package ru.andrw.java.socialnw.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by john on 9/26/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet(name = "FriendsServlet", urlPatterns = {"/friends"})
public class Friends extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger("ru.andrw.java.socialnw.servlet.Friends");
    private String projectName;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext sc = config.getServletContext();
        projectName = (String) sc.getAttribute("projectName");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("pageTitle", "Friends");
        request.setAttribute("PROJECT_NAME", projectName);
        request.setAttribute("includeSection", "/WEB-INF/include/friends.jsp");
        request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
