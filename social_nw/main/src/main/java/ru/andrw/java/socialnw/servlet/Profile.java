package ru.andrw.java.socialnw.servlet;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.andrw.java.socialnw.dao.DaoException;
import ru.andrw.java.socialnw.dao.DaoFactory;
import ru.andrw.java.socialnw.dao.UserProfileDao;
import ru.andrw.java.socialnw.model.UserProfile;
import ru.andrw.java.socialnw.service.ProfileService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by john on 9/25/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile"})
public class Profile extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger("ru.andrw.java.socialnw.servlet.Profile");
    private UserProfileDao profileDao;
    private String projectName;
    private Gson gson;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext sc = config.getServletContext();
        DaoFactory daoFactory =   (DaoFactory) sc.getAttribute("daoFactory");
        projectName = (String) sc.getAttribute("projectName");
        profileDao = daoFactory.getProfileDao();
        ProfileService.setProfileDao(profileDao);
        gson = (Gson) sc.getAttribute("GSON");
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
        else{
            try {
                includeListUserProfiles(request, response,
                        profileDao.getUserProfilesSubList(0L,30L));
            } catch (DaoException e) {
                logger.error("Limits out of bounds",e);
            }
        }
    }

    private void includeListUserProfiles(HttpServletRequest req, HttpServletResponse resp, List profileList)
            throws ServletException, IOException {
        String nextJSP = "/WEB-INF/include/profiles.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        req.setAttribute("profileList", profileList);
        dispatcher.include(req, resp);
    }

}
