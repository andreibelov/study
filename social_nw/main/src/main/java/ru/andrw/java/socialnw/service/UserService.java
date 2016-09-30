package ru.andrw.java.socialnw.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.andrw.java.socialnw.dao.UserDao;

/**
 * Created by john on 9/30/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class UserService {

    private static final Logger logger = LoggerFactory
            .getLogger("ru.andrw.java.socialnw.service.UserService");
    private static final Map<String, ServiceMethod> methodMap;
    private static UserDao userDao;

    static {
        methodMap = new HashMap<>();
        methodMap.put("getform", UserService::getForm);
        methodMap.put("list", UserService::listUsers);
        methodMap.put("add",UserService::addUser);
        methodMap.put("edit",UserService::editUser);
        methodMap.put("remove",UserService::removeUser);
        methodMap.put("search",UserService::searchUser);
    }

    public static void doAction(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        ServiceMethod serviceMethod = methodMap.get(action);
        if(serviceMethod != null) {
            serviceMethod.execute(request,response);
        } else logger.error("Requested method unknown");

    }

    private static void searchUser(HttpServletRequest req,
                                   HttpServletResponse resp)
            throws ServletException, IOException {

    }

    private static void removeUser(HttpServletRequest req,
                                   HttpServletResponse resp)
            throws ServletException, IOException {

    }

    private static void editUser(HttpServletRequest req,
                                 HttpServletResponse resp)
            throws ServletException, IOException {

    }

    private static void addUser(HttpServletRequest req,
                                HttpServletResponse resp)
            throws ServletException, IOException {

    }

    private static void listUsers(HttpServletRequest req,
                                  HttpServletResponse resp)
            throws ServletException, IOException {

    }

    private static void getForm(HttpServletRequest req,
                                HttpServletResponse resp)
            throws ServletException, IOException {
    }

    public static void setUserDao(UserDao userDao) {
        UserService.userDao = userDao;
    }
}
