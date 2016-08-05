package ru.belov.javaee.jsp.service;

import ru.belov.javaee.jsp.dao.UserDAO;
import ru.belov.javaee.jsp.model.ChatUser;
import ru.belov.javaee.jsp.model.Message;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Deque;
import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by john on 7/22/2016.
 */
@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/*"})
public class SecurityFilter implements Filter {

    private UserDAO userDao;
    private MsgQueue msgQueue;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext sc = filterConfig.getServletContext();
        userDao = (UserDAO) sc.getAttribute("userDao");
        msgQueue = (MsgQueue) sc.getAttribute("msgQueue");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);

    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        ChatUser chatUser = (ChatUser) session.getAttribute("chat_user");
        if( chatUser == null){
            Deque<Message> deque = new ConcurrentLinkedDeque<>();
            chatUser = (new ChatUser()).setNickname("chatuser"+ new Random()
                    .nextInt(100))
                    .setJsessionid(session.getId());
            session.setAttribute("chat_user", chatUser);
            session.setAttribute("user_deque", deque);
            msgQueue.registerNewDeque(deque);
            response.sendRedirect(request.getContextPath()+"/chat.jsp");
        } else chain.doFilter(request, response);
    }
}
