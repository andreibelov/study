package ru.andrw.java.socialnw.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.andrw.java.socialnw.dao.DaoException;
import ru.andrw.java.socialnw.dao.DaoFactory;
import ru.andrw.java.socialnw.service.ifaces.ServiceMethod;

import static java.util.Optional.ofNullable;

/**
 * Created by john on 10/9/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class DispatchService{

    private static final Logger logger = LoggerFactory.getLogger(DispatchService.class);
    private static final Map<String, ServiceMethod> getActions = new ConcurrentHashMap<>();
    private static final Map<String, ServiceMethod> postActions = new ConcurrentHashMap<>();
    private static DaoFactory daoFactory;

    static {
        getActions.put("", PageBuilder::getDefault);
        getActions.put("welcome", PageBuilder::welcome);
        getActions.put("home", PageBuilder::getDefault);
        getActions.put("profile", ProfileService::getAction);
        getActions.put("profiles", SearchService::getProfilesList);
        getActions.put("friends", FriendService::getAction);
        getActions.put("admin", AdminService::getAction);
        getActions.put("logout", LoginService::onLogOut);
        getActions.put("inbox", MessageService::getAction);
        getActions.put("news", PostService::getNews);
        getActions.put("wall", PostService::getWall);

        postActions.put("profile", ProfileService::postAction);
        postActions.put("friends", FriendService::postAction);
        postActions.put("logout", LoginService::onLogOut);
        postActions.put("inbox", MessageService::postAction);
        postActions.put("news", PostService::postNews);
        postActions.put("wall", PostService::postWall);
    }

    public static void doGet(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException{
        String uri = request.getRequestURI()
                .substring(request.getContextPath().length()+1);
        Optional<String> o_sectionName = getActions.keySet()
                .stream().filter(uri::equals).findAny();
        if(o_sectionName.isPresent())
            getActions.get(o_sectionName.get()).execute(request,response);
        else response.sendRedirect(request.getContextPath() + "/home"); // Go to home page.
    }

    public static void doPost(HttpServletRequest request,
                             HttpServletResponse response)
            throws ServletException, IOException{
        String uri = request.getRequestURI()
                .substring(request.getContextPath().length()+1);
        Optional<String> o_sectionName = postActions.keySet()
                .stream().filter(uri::equals).findAny();
        if(o_sectionName.isPresent())
            postActions.get(o_sectionName.get()).execute(request,response);
    }

    public static void init(DaoFactory daoFactory) throws ServletException, DaoException {
        DispatchService.daoFactory = daoFactory;
        ProfileService.init(daoFactory);
        MessageService.init(daoFactory);
        FriendService.init(daoFactory);
        PostService.init(daoFactory);
        SearchService.init(daoFactory);
    }
}
