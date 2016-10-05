package ru.andrw.java.socialnw.servlet;

import org.apache.tomcat.util.security.ConcurrentMessageDigest;
import org.apache.tomcat.util.security.MD5Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.andrw.java.socialnw.dao.DaoException;
import ru.andrw.java.socialnw.dao.DaoFactory;
import ru.andrw.java.socialnw.dao.UserDao;
import ru.andrw.java.socialnw.model.User;
import ru.andrw.java.socialnw.service.LoginService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static java.util.Optional.empty;

/**
 * Created by john on 9/25/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class Login extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger("ru.andrw.java.socialnw.servlet.Login");

    private UserDao userDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext sc = config.getServletContext();
        DaoFactory daoFactory =   (DaoFactory) sc.getAttribute("daoFactory");
        userDao = daoFactory.getUserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null)
            response.sendRedirect(request.getContextPath() +"/home"); // Go to some start page.
        else {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            Optional<User> user = empty();
            try {
                user = userDao.findUser(email, encode(password));
            } catch (DaoException e) {
                logger.error("UserDao err", e);
            }

            if (user.isPresent()) LoginService.onLoginSuccess(request,response,user.get());
            else {
                logger.error("Bad login try "+email);
                request.setAttribute("error", "Unknown login, try again"); // Set error msg for ${error}
                request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").include(request, response); // Go back to login page.
            }

        }

    }

    private String encode(String password) {
        return MD5Encoder.encode(ConcurrentMessageDigest.digestMD5(password.getBytes()));
    }
}