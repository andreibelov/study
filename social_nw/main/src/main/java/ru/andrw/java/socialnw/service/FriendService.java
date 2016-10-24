package ru.andrw.java.socialnw.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.andrw.java.socialnw.dao.DaoException;
import ru.andrw.java.socialnw.dao.DaoFactory;
import ru.andrw.java.socialnw.dao.FriendsDao;
import ru.andrw.java.socialnw.model.Profile;
import ru.andrw.java.socialnw.model.auth.User;
import ru.andrw.java.socialnw.model.enums.RowStatus;
import ru.andrw.java.socialnw.service.ifaces.ServiceMethod;

import static java.util.Optional.ofNullable;

/**
 * Created by john on 10/23/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
class FriendService{


    private static final Logger logger = LoggerFactory
            .getLogger(FriendService.class);
    private static final Map<String, ServiceMethod> getMethods = new ConcurrentHashMap<>();
    private static final Map<String, ServiceMethod> postMethods = new ConcurrentHashMap<>();
    private static final String PROFILES = "/WEB-INF/include/sections/profiles.jsp";
    private static final String ATTRIB = "profileList";

    private static FriendsDao friendsDao;

    static {
        getMethods.put("list",FriendService::getFriendList);
        getMethods.put("sent",FriendService::getSentList);
        getMethods.put("received",FriendService::getReceivedList);
        postMethods.put("list",FriendService::postFriendList);
        postMethods.put("sent",FriendService::postSentList);
        postMethods.put("received",FriendService::postReceivedList);
        postMethods.put("block",FriendService::blockUser);
        postMethods.put("unblock",FriendService::unblockUser);
        postMethods.put("send",FriendService::sendRequest);
        postMethods.put("remove",FriendService::removeRequest);
    }

    static void getAction(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        String action = ofNullable(request.getParameter("action")).orElse("list");
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
        String action = ofNullable(request.getParameter("action")).orElse("list");
        ServiceMethod serviceMethod = postMethods.get(action);
        if(serviceMethod != null) {
            serviceMethod.execute(request,response);
        } else {
            logger.error("Requested method unknown");
        }
    }

    private static void sendRequest(HttpServletRequest request, HttpServletResponse response) {
        updateFriendStatus(request, RowStatus.FRIENDLY);
    }

    private static void removeRequest(HttpServletRequest request, HttpServletResponse response) {
        updateFriendStatus(request, RowStatus.UNDEFINED);
    }

    private static void blockUser(HttpServletRequest request, HttpServletResponse response) {
        updateFriendStatus(request, RowStatus.HOSTILE);
    }

    private static void unblockUser(HttpServletRequest request, HttpServletResponse response) {
        updateFriendStatus(request, RowStatus.UNDEFINED);
    }

    private static void getSentList(HttpServletRequest request,
                                    HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute(ATTRIB, getFollowees(request));
        PageBuilder.getDefault(request,response);

    }

    private static void postReceivedList(HttpServletRequest request,
                                         HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(ATTRIB, getFollowees(request));
        request.getRequestDispatcher(PROFILES).forward(request,response);
    }

    private static void getReceivedList(HttpServletRequest request,
                                        HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute(ATTRIB, getFollowers(request));
        PageBuilder.getDefault(request,response);

    }

    private static void postSentList(HttpServletRequest request,
                                     HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(ATTRIB, getFollowers(request));
        request.getRequestDispatcher(PROFILES).forward(request,response);
    }

    private static void getFriendList(HttpServletRequest request,
                                      HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(ATTRIB, getFriendsProfiles(request));
        PageBuilder.getDefault(request,response);
    }

    private static void postFriendList(HttpServletRequest request,
                                       HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(ATTRIB, getFriendsProfiles(request));
        request.getRequestDispatcher(PROFILES)
                .forward(request,response);
    }

    private static void updateFriendStatus(HttpServletRequest request, RowStatus status) {
        HttpSession session = request.getSession();
        Long requesterId = ((User) session.getAttribute("user")).getId();
        Optional<String> o_userid = ofNullable(request.getParameter("id"));
        if(o_userid.isPresent() && !requesterId.equals(Long.parseLong(o_userid.get())))
            friendsDao.friendUpdate(requesterId,Long.parseLong(o_userid.get()),status);
        else logger.warn("Fail update status try");
    }

    private static List<Profile> getFollowers(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String s_userid = ofNullable((String) request.getAttribute("id"))
                .orElse(((User) session.getAttribute("user")).getId().toString());
        String s_offset = ofNullable((String) request.getAttribute("offset"))
                .orElse(Integer.toString(0));
        String s_limit = ofNullable((String) request.getAttribute("limit"))
                .orElse(Integer.toString(30));
        int offset = Integer.parseInt(s_offset);
        int limit = Integer.parseInt(s_limit);
        return friendsDao.getReceivedSublist(Long.parseLong(s_userid),offset,limit);
    }

    private static Object getFollowees(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String s_userid = ofNullable(request.getParameter("id"))
                .orElse(((User) session.getAttribute("user")).getId().toString());
        String s_offset = ofNullable(request.getParameter("offset"))
                .orElse(Integer.toString(0));
        String s_limit = ofNullable(request.getParameter("limit"))
                .orElse(Integer.toString(30));
        int offset = Integer.parseInt(s_offset);
        int limit = Integer.parseInt(s_limit);
        return friendsDao.getSentSublist(Long.parseLong(s_userid),offset,limit);
    }

    private static List<Profile> getFriendsProfiles(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String s_userid = ofNullable(request.getParameter("id"))
                .orElse(((User) session.getAttribute("user")).getId().toString());
        String s_offset = ofNullable(request.getParameter("offset"))
                .orElse(Integer.toString(0));
        String s_limit = ofNullable(request.getParameter("limit"))
                .orElse(Integer.toString(30));
        int offset = Integer.parseInt(s_offset);
        int limit = Integer.parseInt(s_limit);
        return friendsDao.getFriendsSublist(Long.parseLong(s_userid),offset,limit);
    }

    static void init(DaoFactory daoFactory) throws ServletException, DaoException {
       friendsDao = daoFactory.getFriendsDao();
    }
}
