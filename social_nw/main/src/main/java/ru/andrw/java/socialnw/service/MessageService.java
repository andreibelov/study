package ru.andrw.java.socialnw.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.andrw.java.socialnw.dao.DaoException;
import ru.andrw.java.socialnw.dao.DaoFactory;
import ru.andrw.java.socialnw.dao.IMDao;
import ru.andrw.java.socialnw.model.auth.User;
import ru.andrw.java.socialnw.model.chat.Conversation;
import ru.andrw.java.socialnw.model.chat.IMessage;
import ru.andrw.java.socialnw.service.ifaces.ServiceMethod;

import static java.util.Optional.ofNullable;

/**
 * Created by john on 10/24/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
class MessageService {


    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
    private static final Map<String, ServiceMethod> getMethods = new ConcurrentHashMap<>();
    private static final Map<String, ServiceMethod> postMethods = new ConcurrentHashMap<>();
    private static IMDao messageDao;

    static {
        getMethods.put("list",MessageService::getConvList);
        postMethods.put("send", MessageService::sendMessage);
        postMethods.put("create", MessageService::createChat);
        postMethods.put("delete", MessageService::deleteMessage);
        postMethods.put("remove", MessageService::removeChat);
        postMethods.put("invite", MessageService::inviteUser);
        postMethods.put("kick", MessageService::kickUser);
    }

    private static void removeChat(HttpServletRequest request,
                                   HttpServletResponse response)
            throws ServletException, IOException {

    }

    private static void kickUser(HttpServletRequest request,
                                 HttpServletResponse response)
            throws ServletException, IOException {

    }

    private static void inviteUser(HttpServletRequest request,
                                   HttpServletResponse response)
            throws ServletException, IOException {

    }

    private static void deleteMessage(HttpServletRequest request,
                                      HttpServletResponse response)
            throws ServletException, IOException {

    }

    private static void createChat(HttpServletRequest request,
                                   HttpServletResponse response)
            throws ServletException, IOException {

    }

    private static void sendMessage(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {



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
            response.sendRedirect(request.getContextPath() + "/inbox"); // Go to home page.
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

    private static void getConvList(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Long requesterId = ((User) session.getAttribute("user")).getId();
        String s_offset = ofNullable((String) request.getAttribute("offset"))
                .orElse(Integer.toString(0));
        String s_limit = ofNullable((String) request.getAttribute("limit"))
                .orElse(Integer.toString(30));
        int offset = Integer.parseInt(s_offset);
        int limit = Integer.parseInt(s_limit);
        Map<Conversation,IMessage> map = messageDao.getConversationList(requesterId,offset,limit);
        request.setAttribute("conversations",map.entrySet());
    }

    static void init(DaoFactory daoFactory) throws ServletException, DaoException {
        messageDao = daoFactory.getIMDao();
    }


}
