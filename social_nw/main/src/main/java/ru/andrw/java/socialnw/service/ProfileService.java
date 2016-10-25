package ru.andrw.java.socialnw.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.andrw.java.socialnw.dao.DaoException;
import ru.andrw.java.socialnw.dao.DaoFactory;
import ru.andrw.java.socialnw.dao.FriendsDao;
import ru.andrw.java.socialnw.dao.UserProfileDao;
import ru.andrw.java.socialnw.model.Profile;
import ru.andrw.java.socialnw.model.view.Section;
import ru.andrw.java.socialnw.model.auth.User;
import ru.andrw.java.socialnw.model.enums.Countries;
import ru.andrw.java.socialnw.model.enums.Gender;
import ru.andrw.java.socialnw.service.ifaces.ServiceMethod;
import ru.andrw.java.socialnw.util.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Optional.ofNullable;
import static java.util.UUID.fromString;

/**
 * Created by john on 9/26/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
class ProfileService{

    private static final Logger logger = LoggerFactory
            .getLogger(ProfileService.class);
    private static final Map<String, ServiceMethod> getMethods = new ConcurrentHashMap<>();
    private static final Map<String, ServiceMethod> postMethods = new ConcurrentHashMap<>();
    private static final String PROFILES = "/WEB-INF/include/sections/profiles.jsp";
    private static final String ATTRIB = "profileList";
    private static UserProfileDao profileDao;
    private static FriendsDao friendsDao;



    static {
        postMethods.put("getform", ProfileService::getEditForm);
        postMethods.put("list", ProfileService::postProfilesList);
        postMethods.put("append", ProfileService::listAppend);
        postMethods.put("add", ProfileService::addUserProfile);
        postMethods.put("edit", ProfileService::editUserProfile);
        postMethods.put("remove", ProfileService::removeUserProfile);
        postMethods.put("update", ProfileService::updateProfile);

        getMethods.put("edit", ProfileService::getEditProfile);
        getMethods.put("show",ProfileService::showProfile);
    }

    static void getAction(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String action = ofNullable(request.getParameter("action")).orElse("show");
        ServiceMethod serviceMethod = getMethods.get(action);
        if(serviceMethod != null) {
            serviceMethod.execute(request,response);
        } else {
            logger.error("Requested method unknown");
            response.sendRedirect(request.getContextPath() + "/home"); // Go to home page.
        }

    }

    static void postAction(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        ServiceMethod serviceMethod = postMethods.get(action);
        if(serviceMethod != null) {
            serviceMethod.execute(request,response);
        } else {
            logger.error("Requested method unknown");
            response.sendRedirect(request.getContextPath() + "/home"); // Go to home page.
        }
    }

    private static void getEditProfile(HttpServletRequest request,
                                       HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Long id = ((User) session.getAttribute("user")).getId();
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        Profile profile = profileDao.getUserProfileById(id).get();
        request.setAttribute("profile", profile);
        Section section = (new Section())
                .setSectionName("Edit profile")
                .setPageTitle("Edit profile")
                .setCssFile("profile.css")
                .setJsFile("profile.js")
                .setJspFile("profile-edit.jsp");
        request.setAttribute("action", "update");
        PageBuilder.buildPage(request, response, section);
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

    private static void postProfilesList(HttpServletRequest req,
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
            req.setAttribute(ATTRIB,profileList);
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
                    .setCountry(Countries.valueOf(req.getParameter("country")))
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

        Optional<Map<String,String>> o_params = Validator.getValidParams(req);
        String nextJSP = "/WEB-INF/include/forms/profile-edit.jsp";;
        Optional<Profile> o_profile = profileDao.getUserProfileById(profileId);
        Profile profile = o_profile.isPresent() ? o_profile.get() : new Profile();
        if (o_params.isPresent())try {
            Map<String,String> map = o_params.get();
            profile.setId(profileId)
                .setAccessLevel(3) //TODO
                .setEmail(map.get("email"))
                .setLogin(map.get("login"));
            profile.setBirthDate(format.parse(map.get("birthDate")))
                    .setCountry(Countries.valueOf(map.get("country")))
                    .setCity(map.get("city"))
                    .setFirstName(map.get("firstName"))
                    .setLastName(map.get("lastName"))
                    .setPhone(map.get("mobile"))
                    .setPhoto(fromString(map.get("photo")))
                    .setSex(Gender.values()[Integer.parseInt(map.get("gender"))])
                    .setStatus("");

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
            }
        } catch (ParseException e) {
            logger.error("Could not parse date provided",e);
            req.setAttribute("userProfileId",profileId);
            req.setAttribute("message","Could not parse date provided!");
            req.setAttribute("action","edit");
            req.setAttribute("profile",profile);
        } else {
            logger.warn("Provided information is not valid!");
            req.setAttribute("userProfileId",profileId);
            req.setAttribute("message","Provided information is not valid!");
            req.setAttribute("action","edit");
            req.setAttribute("profile",profile);
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

    private static void showProfile(HttpServletRequest request,
                                    HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String profileId = ofNullable(request.getParameter("id"))
                .orElse(user.getId().toString());
        Optional<Profile> byId = profileDao.getUserProfileById(Long.parseLong(profileId));
        int status = friendsDao.friendsStatus(user.getId(), Long.parseLong(profileId));
        if(byId.isPresent())request.setAttribute("profile",byId.get());
        request.setAttribute("friendStatus",status);
        PageBuilder.getDefault(request,response);
    }

    private static void updateProfile(HttpServletRequest req,
                                      HttpServletResponse resp)
            throws ServletException, IOException {
        Long profileId = Long.valueOf(req.getParameter("userid"));
        String pattern = "dd-mm-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Optional<Map<String,String>> o_params = Validator.getValidParams(req);
        if (o_params.isPresent()) try {
            Map<String,String> map = o_params.get();
            Optional<Profile> o_profile = profileDao.getUserProfileById(profileId);
            Profile profile = o_profile.isPresent() ? o_profile.get() : new Profile();
            profile.setId(profileId)
                    .setAccessLevel(3) //TODO
                    .setEmail(map.get("email"))
                    .setLogin(map.get("login"));
            profile.setBirthDate(format.parse(map.get("birthDate")))
                    .setCountry(Countries.valueOf(map.get("country")))
                    .setCity(map.get("city"))
                    .setFirstName(map.get("firstName"))
                    .setLastName(map.get("lastName"))
                    .setPhone(map.get("mobile"))
                    .setPhoto(fromString(map.get("photo")))
                    .setSex(Gender.values()[Integer.parseInt(map.get("gender"))])
                    .setStatus("");
            profileDao.updateUserProfile(profile);
        } catch (ParseException | DaoException e) {
            logger.error("Could not parse date provided",e);
        }
    }

    static void init(DaoFactory daoFactory) throws ServletException, DaoException {
        profileDao = daoFactory.getProfileDao();
        friendsDao = daoFactory.getFriendsDao();
    }
}
