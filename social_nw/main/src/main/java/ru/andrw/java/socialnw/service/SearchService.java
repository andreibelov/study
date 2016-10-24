package ru.andrw.java.socialnw.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.andrw.java.socialnw.dao.DaoException;
import ru.andrw.java.socialnw.dao.DaoFactory;
import ru.andrw.java.socialnw.dao.UserProfileDao;
import ru.andrw.java.socialnw.model.Profile;
import ru.andrw.java.socialnw.model.view.Section;
import ru.andrw.java.socialnw.service.ifaces.ServiceMethod;

import static java.util.Optional.ofNullable;

/**
 * Created by john on 10/24/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
class SearchService {

    private static final Logger logger = LoggerFactory
            .getLogger(SearchService.class);
    private static final Map<String, ServiceMethod> getMethods = new ConcurrentHashMap<>();
    private static final String PROFILES = "/WEB-INF/include/sections/profiles.jsp";
    private static final String ATTRIB = "profileList";
    private static UserProfileDao profileDao;

    static void getProfilesList(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {

        String s_offset = ofNullable(request.getParameter("offset"))
                .orElse(Integer.toString(0));
        String s_limit = ofNullable(request.getParameter("limit"))
                .orElse(Integer.toString(30));
        int offset = Integer.parseInt(s_offset);
        int limit = Integer.parseInt(s_limit);
        List<Profile> profileList = profileDao
                .getUserProfilesSubList(offset,limit);
        request.setAttribute(ATTRIB,profileList);
        Section section = (new Section())
                .setSectionName("Profiles")
                .setPageTitle("Profiles")
                .setCssFile("profiles.css")
                .setJsFile("friends.js")
                .setJspFile("profiles.jsp");
        PageBuilder.buildPage(request,response,section);
    }

    static void init(DaoFactory daoFactory) throws ServletException, DaoException {
        profileDao = daoFactory.getProfileDao();
    }
}
