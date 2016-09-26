package ru.andrw.java.socialnw.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.andrw.java.socialnw.dao.DaoFactory;
import ru.andrw.java.socialnw.dao.UserProfileDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by john on 9/26/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class ProfileService {

    private static final Map<String, ServiceMethod> methods;
    private static final Logger logger = LoggerFactory.getLogger("ru.andrw.java.socialnw.service.ProfileService");
    private static DaoFactory daoFactory;

    static {
        methods = new HashMap<>();
        methods.put("getform", ProfileService::getEditForm);
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
        } else logger.error("");
    }

    private static void getEditForm(HttpServletRequest request,
                                    HttpServletResponse response) {
        String nextJSP = "/WEB-INF/include/profile-edit.jsp";
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(nextJSP);
        try {
            dispatcher.include(request, response);
        } catch (ServletException | IOException e) {
            logger.error("File "+nextJSP+" not found", e);
        }
    }

    private static void addUserProfile(HttpServletRequest httpServletRequest,
                                       HttpServletResponse httpServletResponse) {

    }

    private static void editUserProfile(HttpServletRequest request,
                                        HttpServletResponse response){

    }

    private static void removeUserProfile(HttpServletRequest request,
                                          HttpServletResponse response){

    }

    public static void setDaoFactory(DaoFactory daoFactory) {
        ProfileService.daoFactory = daoFactory;
    }
}
