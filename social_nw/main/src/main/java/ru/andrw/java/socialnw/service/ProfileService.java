package ru.andrw.java.socialnw.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.andrw.java.socialnw.dao.DaoException;
import ru.andrw.java.socialnw.dao.UserProfileDao;
import ru.andrw.java.socialnw.model.UserProfile;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by john on 9/26/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class ProfileService {

    private static final Logger logger = LoggerFactory
            .getLogger(ProfileService.class);
    private static final Map<String, ServiceMethod> methods = new ConcurrentHashMap<>();;
    private static UserProfileDao profileDao;

    static {
        methods.put("getform", ProfileService::getEditForm);
        methods.put("list", ProfileService::listProfiles);
        methods.put("append", ProfileService::listAppend);
        methods.put("add",ProfileService::addUserProfile);
        methods.put("edit",ProfileService::editUserProfile);
        methods.put("remove",ProfileService::removeUserProfile);
    }

    public static void doAction(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        ServiceMethod serviceMethod = methods.get(action);
        if(serviceMethod != null) {
            serviceMethod.execute(request,response);
        } else logger.error("Requested method unknown");
    }

    private static void listAppend(HttpServletRequest req,
                                   HttpServletResponse resp)
            throws ServletException, IOException {

        String s_offset = req.getParameter("offset");
        String s_limit = req.getParameter("limit");
        Integer offset, limit;
        if(s_offset != null && s_limit != null){
            offset = Integer.valueOf(s_offset);
            limit = Integer.valueOf(s_limit);
        } else {
            offset = 0;
            limit = 0;
        }
        try {
            String nextJSP = "/WEB-INF/include/admin/profiles-rows.jsp";

            List<UserProfile> profileList = profileDao
                    .getUserProfilesSubList(offset,limit);
            req.setAttribute("profileList",profileList);
            req.getServletContext()
                    .getRequestDispatcher(nextJSP)
                    .include(req, resp);
        } catch (DaoException e) {
            logger.error("Limits out of bounds",e);
        }
    }

    public static void listProfiles(HttpServletRequest req,
                                     HttpServletResponse resp)
            throws ServletException, IOException {

        String s_offset = req.getParameter("offset");
        String s_limit = req.getParameter("limit");
        Integer offset, limit;
        if(s_offset != null && s_limit != null){
            offset = Integer.valueOf(s_offset);
            limit = Integer.valueOf(s_limit);
        } else {
            offset = 0;
            limit = 30;
        }
        try {
            String nextJSP = "/WEB-INF/include/admin/profiles-table.jsp";

            List<UserProfile> profileList = profileDao
                    .getUserProfilesSubList(offset,limit);
            req.setAttribute("profileList",profileList);
            req.getServletContext()
                    .getRequestDispatcher(nextJSP)
                    .include(req, resp);
        } catch (DaoException e) {
            logger.error("Limits out of bounds",e);
        }

    }

    private static void getEditForm(HttpServletRequest req,
                                    HttpServletResponse resp)
            throws ServletException, IOException {
        String s = req.getParameter("userProfileId");
        if (s != null){
            Long userProfileId = Long.valueOf(s);
            Optional<UserProfile> profile = profileDao.getUserProfileById(userProfileId);

            if (profile.isPresent()) {
                req.setAttribute("userProfileId", userProfileId);
                req.setAttribute("action", "edit");
                req.setAttribute("profile", profile.get());
            }
        };

        String nextJSP = "/WEB-INF/include/forms/profile-edit.jsp";
        req.getServletContext()
                .getRequestDispatcher(nextJSP)
                .include(req, resp);
    }

    private static void addUserProfile(HttpServletRequest req,
                                       HttpServletResponse resp)
            throws ServletException, IOException {
        String pattern = "dd-mm-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        UserProfile profile = null;
        try {
            profile = (new UserProfile())
                    .setName(req.getParameter("name"))
                    .setUserid(Long.valueOf(req.getParameter("userid")))
                    .setLastName(req.getParameter("lastName"))
                    .setCountry(req.getParameter("country"))
                    .setCity(req.getParameter("city"))
                    .setEmail(req.getParameter("email"))
                    .setStatus(req.getParameter("status"))
                    .setPhotoid(req.getParameter("photoUuid"))
                    .setBirthDate(format.parse(req.getParameter("birthDate")));
        } catch (ParseException e) {
            logger.error("Could not parse date provided",e);
        }
        long userProfileId = 0;
        String nextJSP;
        try {
            userProfileId = profileDao.addUserProfile(profile);
            nextJSP = "/WEB-INF/include/admin/profiles-table.jsp";
            req.setAttribute("message","User added successfully");
            req.setAttribute("profileList", profileDao.getUserProfilesSubList(0,30));
        } catch (DaoException e) {
            logger.error(e.getMessage(),e);
            req.setAttribute("userProfileId",userProfileId);
            req.setAttribute("message","User not updated!");
            req.setAttribute("action","edit");
            req.setAttribute("profile",profile);
            nextJSP = "/WEB-INF/include/forms/profile-edit.jsp";
        }
        req.getServletContext()
                .getRequestDispatcher(nextJSP)
                .include(req, resp);
    }

    private static void editUserProfile(HttpServletRequest req,
                                        HttpServletResponse resp)
            throws ServletException, IOException {
        Long userProfileId = Long.valueOf(req.getParameter("userProfileId"));
        String pattern = "dd-mm-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        UserProfile profile = null;
        try {
            profile = (new UserProfile())
                    .setName(req.getParameter("name"))
                    .setUserid(Long.valueOf(req.getParameter("userid")))
                    .setLastName(req.getParameter("lastName"))
                    .setCountry(req.getParameter("country"))
                    .setCity(req.getParameter("city"))
                    .setEmail(req.getParameter("email"))
                    .setStatus(req.getParameter("status"))
                    .setPhotoid(req.getParameter("photoUuid"))
                    .setId(userProfileId)
                    .setBirthDate(format.parse(req.getParameter("birthDate")));
        } catch (ParseException e) {
            logger.error("Could not parse date provided",e);
        }
        String nextJSP;
        try {
            profileDao.updateUserProfile(profile);
            nextJSP = "/WEB-INF/include/admin/profiles-table.jsp";
            req.setAttribute("message","User updated successfully");
            req.setAttribute("profileList", profileDao.getUserProfilesSubList(0,30));
        } catch (DaoException e) {
            logger.error("Profile not updated",e);
            req.setAttribute("userProfileId",userProfileId);
            req.setAttribute("message","User not updated!");
            req.setAttribute("action","edit");
            req.setAttribute("profile",profile);
            nextJSP = "/WEB-INF/include/forms/profile-edit.jsp";
        }
        req.getServletContext()
                .getRequestDispatcher(nextJSP)
                .include(req, resp);
    }

    private static void removeUserProfile(HttpServletRequest req,
                                         HttpServletResponse resp) {
        long userProfileId = Long.valueOf(req.getParameter("userProfileId"));
        boolean confirm = profileDao.deleteUserProfile(userProfileId);
        if (confirm){
            String message = "The profile has been successfully removed.";
            req.setAttribute("message", message);
        }
    }

    public static void setProfileDao(UserProfileDao profileDao) {
        ProfileService.profileDao = profileDao;
    }
}
