package ru.andrw.java.socialnw.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.andrw.java.socialnw.dao.DaoFactory;
import ru.andrw.java.socialnw.model.Section;
import ru.andrw.java.socialnw.service.ProfileService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by john on 9/25/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile"})
public class Profile extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger(Profile.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext sc = config.getServletContext();
        DaoFactory daoFactory = (DaoFactory) sc.getAttribute("daoFactory");
        ProfileService.setProfileDao(daoFactory.getProfileDao());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action!=null) ProfileService.doAction(request,response);
        else ProfileService.listProfiles(request,response);
    }
}
