package ru.belov.study.proj5.servlet;

import ru.belov.study.proj5.core.OptionalConsumer;
import ru.belov.study.proj5.dao.DaoException;
import ru.belov.study.proj5.dao.UserDao;
import ru.belov.study.proj5.model.chat.ChatMessage;
import ru.belov.study.proj5.service.ChatQueue;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by john on 7/22/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login.do"})
public class Login extends javax.servlet.http.HttpServlet {
    private UserDao userDao;
    private ChatQueue msgQueue;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext sc = config.getServletContext();
        userDao = (UserDao) sc.getAttribute("userDao");
        msgQueue = (ChatQueue) sc.getAttribute("msgQueue");
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("j_username");
        String password = request.getParameter("j_password");
        try {
            OptionalConsumer.of(userDao.find(login,password))
                    .ifPresent(usr -> {
                        Deque<ChatMessage> deque = new ConcurrentLinkedDeque<>();
                        request.getSession().setAttribute("user", usr);// Put user in session.
                        request.getSession().setAttribute("user_deque", deque);
                        Cookie userName = new Cookie("user", usr.getLogin());
                        response.addCookie(userName);
                        msgQueue.registerNewDeque(deque);
                        try {
                            response.sendRedirect(request.getContextPath()+"/chat.jsp");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }})
                    .ifNotPresent(() -> {
                        request.setAttribute("error", "Unknown login, try again"); // Set error msg for ${error}
                        try {
                            request.getRequestDispatcher("/login2.jsp").forward(request, response); // Go back to login page.);
                        } catch (ServletException | IOException e1) {
                            e1.printStackTrace();
                        }
                    });
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}
