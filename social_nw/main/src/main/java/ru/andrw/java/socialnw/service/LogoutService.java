package ru.andrw.java.socialnw.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.andrw.java.socialnw.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by john on 9/26/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class LogoutService {

    private static final Logger logger = LoggerFactory
            .getLogger("ru.andrw.java.socialnw.service.LogoutService");

    public static void onLogOut(HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
        //invalidate the session if exists
        HttpSession session = request.getSession(false);
        if(session != null){
            User user = (User) session.getAttribute("user");
            session.removeAttribute("user");
            logger.info("User "+user.getLogin()+" successfully logged out.");
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() +"/login");
    }
}
