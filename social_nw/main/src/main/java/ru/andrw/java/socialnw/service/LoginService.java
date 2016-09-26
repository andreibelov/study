package ru.andrw.java.socialnw.service;

import org.slf4j.Logger;
import ru.andrw.java.socialnw.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Created by john on 9/26/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class LoginService {

    public static void onLoginSuccess(HttpServletRequest request,
                            HttpServletResponse response, User user, Logger logger)
                                                    throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("user", user); // Put user in a session.
        logger.info("User "+user.getLogin()+" successfully logged in.");
        Cookie userName = new Cookie("user", user.getLogin());
        response.addCookie(userName);
        response.sendRedirect(request.getContextPath() + "/home"); // Go to some start page.
    }
}
