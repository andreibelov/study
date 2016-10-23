package ru.andrw.java.socialnw.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.andrw.java.socialnw.dao.DaoException;
import ru.andrw.java.socialnw.dao.UserProfileDao;
import ru.andrw.java.socialnw.model.Profile;
import ru.andrw.java.socialnw.model.enums.Gender;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.UUID.fromString;

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

            List<Profile> profileList = profileDao
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

            List<Profile> profileList = profileDao
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
            Optional<Profile> profile = profileDao.getUserProfileById(userProfileId);

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
        Profile profile = null;
        try {
            profile = (new Profile());
            profile.setEmail(req.getParameter("email"))
                    .setLogin(req.getParameter("login"));
            profile.setBirthDate(format.parse(req.getParameter("birthDate")))
                    .setCountry(req.getParameter("country"))
                    .setCity(req.getParameter("city"))
                    .setFirstName(req.getParameter("firstName"))
                    .setLastName(req.getParameter("lastName"))
                    .setPhone(req.getParameter("mobile"))
                    .setPhoto(fromString(req.getParameter("photo")))
                    .setSex(Gender.values()[Integer.parseInt(req.getParameter("gender"))]);
        } catch (ParseException e) {
            logger.error("Could not parse date provided",e);
        }
        long userProfileId = 0;
        String nextJSP;
        try {
            userProfileId = profileDao.addUserProfile(profile).getId();
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
        Long profileId = Long.valueOf(req.getParameter("userProfileId"));
        String pattern = "dd-mm-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Optional<Profile> o_profile = profileDao.getUserProfileById(profileId);
        Profile profile = o_profile.isPresent() ? o_profile.get() : new Profile();
        try {
            profile.setId(profileId)
                .setAccessLevel(3) //TODO
                .setEmail(req.getParameter("email"))
                .setLogin(req.getParameter("login"));
            profile.setBirthDate(format.parse(req.getParameter("birthDate")))
                    .setCountry(req.getParameter("country"))
                    .setCity(req.getParameter("city"))
                    .setFirstName(req.getParameter("firstName"))
                    .setLastName(req.getParameter("lastName"))
                    .setPhone(req.getParameter("mobile"))
                    .setPhoto(fromString(req.getParameter("photo")))
                    .setSex(Gender.values()[Integer.parseInt(req.getParameter("gender"))])
                    .setStatus("");
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
            req.setAttribute("userProfileId",profileId);
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
        String message;
        try {

            profileDao.deleteUserProfile(userProfileId);
            message = "The profile has been successfully removed.";

        } catch (DaoException ex){
            message = "The profile was not removed =(";
        }
            req.setAttribute("message", message);

    }

    public static void setProfileDao(UserProfileDao profileDao) {
        ProfileService.profileDao = profileDao;
    }
}
