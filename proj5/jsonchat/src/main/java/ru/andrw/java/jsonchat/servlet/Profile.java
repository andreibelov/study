package ru.andrw.java.jsonchat.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.andrw.java.jsonchat.dao.DaoException;
import ru.andrw.java.jsonchat.dao.DaoFactory;
import ru.andrw.java.jsonchat.dao.UserProfileDao;
import ru.andrw.java.jsonchat.model.UserProfile;

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
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "UserProfile", urlPatterns = {"/profile"} )
public class Profile extends HttpServlet {

    private UserProfileDao profileDao;
    private Gson gson;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext sc = config.getServletContext();
        DaoFactory daoFactory =   (DaoFactory) sc.getAttribute("daoFactory");
        profileDao = daoFactory.getProfileDao();
        gson = (Gson) sc.getAttribute("GSON");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("searchAction");
        if (action!=null){
            switch (action) {           
            case "searchById":
                searchUserProfileById(req, resp);
                break;           
            case "searchByName":
                searchProfileByName(req, resp);
                break;
            }
        }
    }

    private void searchUserProfileById(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        long idUserProfile = Integer.valueOf(req.getParameter("idUserProfile"));
        UserProfile profile = null;
        try {
            profile = profileDao.getUserProfile(idUserProfile);
        } catch (Exception ex) {
            Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
        }
        req.setAttribute("profile", profile);
        req.setAttribute("action", "edit");
        String nextJSP = "/WEB-INF/jsp/profile-edit.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.include(req, resp);
    }
    
    private void searchProfileByName(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String profileName = req.getParameter("profileName");
        List<UserProfile> result = profileDao.searchUserProfilesByName(profileName);
        includeListUserProfiles(req, resp, result);
    }

    private void includeListUserProfiles(HttpServletRequest req, HttpServletResponse resp, List profileList)
            throws ServletException, IOException {
        String nextJSP = "/WEB-INF/jsp/profiles.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        req.setAttribute("profileList", profileList);
        dispatcher.include(req, resp);
    }   
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action!=null){
            switch (action) {
                case "getform":
                    getEditor(req, resp);
                    break;
                case "add":
                    addUserProfileAction(req, resp);
                    break;
                case "edit":
                    editUserProfileAction(req, resp);
                    break;
                case "remove":
                    removeUserProfileByName(req, resp);
                    break;
            }
        }else{
            List<UserProfile> result = profileDao.getAllUserProfiles();
            includeListUserProfiles(req, resp, result);
        }

    }

    private void getEditor(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String nextJSP = "/WEB-INF/jsp/profile-edit.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.include(req, resp);

    }

    private void addUserProfileAction(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        UserProfile profile = (new UserProfile())
                .setName(req.getParameter("name"))
                .setLastName(req.getParameter("lastName"))
                .setBirthDate(req.getParameter("birthDate"))
                .setCountry(req.getParameter("country"))
                .setCity(req.getParameter("city"))
                .setEmail(req.getParameter("email"));
        long idUserProfile = 0;
        try {
            idUserProfile = profileDao.addUserProfile(profile);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        req.setAttribute("idUserProfile", idUserProfile);
        String message = "The profile has been successfully updated.";
        req.setAttribute("message", message);
        List<UserProfile> profileList = profileDao.getAllUserProfiles();
        includeListUserProfiles(req, resp, profileList);
    }

    private void editUserProfileAction(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        long idUserProfile = Long.valueOf(req.getParameter("idUserProfile"));
        UserProfile profile = (new UserProfile())
                .setName(req.getParameter("name"))
                .setLastName(req.getParameter("lastName"))
                .setBirthDate(req.getParameter("birthDate"))
                .setCountry(req.getParameter("country"))
                .setCity(req.getParameter("city"))
                .setEmail(req.getParameter("email"))
                .setId(idUserProfile);
        boolean success = profileDao.updateUserProfile(profile);
        String message = null;
        if (success) {
            message = "The profile has been successfully updated.";
        }
        List<UserProfile> profileList = profileDao.getAllUserProfiles();
        req.setAttribute("idUserProfile", idUserProfile);
        req.setAttribute("message", message);
        includeListUserProfiles(req, resp, profileList);
    }  

    private void removeUserProfileByName(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        long idUserProfile = Long.valueOf(req.getParameter("idUserProfile"));
        boolean confirm = profileDao.deleteUserProfile(idUserProfile);
        if (confirm){
            String message = "The profile has been successfully removed.";
            req.setAttribute("message", message);
        }
        List<UserProfile> profileList = profileDao.getAllUserProfiles();
        includeListUserProfiles(req, resp, profileList);
    }

}
