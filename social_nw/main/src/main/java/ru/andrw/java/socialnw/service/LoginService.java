package ru.andrw.java.socialnw.service;

import org.apache.tomcat.util.security.ConcurrentMessageDigest;
import org.apache.tomcat.util.security.MD5Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.andrw.java.socialnw.dao.DaoException;
import ru.andrw.java.socialnw.dao.TokensDao;
import ru.andrw.java.socialnw.dao.UserDao;
import ru.andrw.java.socialnw.dao.UserProfileDao;
import ru.andrw.java.socialnw.model.SectionModule;
import ru.andrw.java.socialnw.model.User;
import ru.andrw.java.socialnw.model.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;


/**
 * Created by john on 9/26/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class LoginService {

    private static final Logger logger = LoggerFactory
            .getLogger("ru.andrw.java.socialnw.service.LoginService");

    private static final Map<String, SectionModule> sections = new ConcurrentHashMap<>();
    private static final Map<String, ServiceMethod> methods = new ConcurrentHashMap<>();
    private static UserProfileDao profileDao;
    private static UserDao userDao;
    private static TokensDao tokensDao;

    static {
        sections.put("login", (new SectionModule())
                .setSectionName("Login")
                .setPageTitle("Login")
                .setCssFile("login.css")
                .setJsFile("login.js")
                .setJspFile("forms/login-form.jsp")
                .setData("login")
        );
        sections.put("signup", (new SectionModule())
                .setSectionName("Signup")
                .setPageTitle("Register")
                .setCssFile("login.css")
                .setJsFile("login.js")
                .setJspFile("forms/login-form.jsp")
                .setData("signup")
        );
        sections.put("recover", (new SectionModule())
                .setSectionName("Recover password")
                .setPageTitle("Recover password")
                .setCssFile("login.css")
                .setJsFile("login.js")
                .setJspFile("forms/recover-form.jsp")
                .setData("recover")
        );

        methods.put("default", LoginService::tryLogin);
        methods.put("login", LoginService::tryLogin);
        methods.put("signup", LoginService::onSignUp);
        methods.put("recover", LoginService::recoverPass);
    }

    public static void performGetAction(HttpServletRequest request,
                                        HttpServletResponse response)
            throws ServletException, IOException {
        SectionModule section;
        Optional<String> s_action = ofNullable(request.getParameter("action"));
        section = (s_action.isPresent() && sections.containsKey(s_action.get())) ?
                sections.get(s_action.get()) : sections.get("login");
        request.setAttribute("section", section);
        request.setAttribute("logindata", section.getData());
        request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").include(request, response);

    }

    public static void performPostAction(HttpServletRequest request,
                                         HttpServletResponse response)
            throws ServletException, IOException {
        Optional<String> s_action = ofNullable(request.getParameter("action"));
        if(s_action.isPresent()) {
            ((methods.containsKey(s_action.get())) ? methods.get(s_action.get()) :
                    methods.get("default")).execute(request,response);
        } else {
            logger.error("Requested method unknown");
            forwardToLogin(request,response,"Requested method unknown");
        }
    }

    private static void tryLogin(HttpServletRequest request,
                                 HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Optional<User> user = empty();
        try {
            user = userDao.findUser(email, encode(password));
        } catch (DaoException e) {
            logger.error("UserDao err", e);
        }

        if (user.isPresent()) {
            onLoginSuccess(request,response,user.get());
            response.sendRedirect(request.getContextPath() + "/home"); // Go to some start page.
        } else {
            logger.warn("Bad login try "+email);
            forwardToLogin(request, response, "Provided credentials are not valid, try again!");
        }

    }

    private static void forwardToLogin(HttpServletRequest request,
                                       HttpServletResponse response, String message)
                                    throws ServletException, IOException {
        request.setAttribute("errormessage", message); // Set error msg for ${error}
        SectionModule section = sections.get("login");
        request.setAttribute("section", section);
        request.setAttribute("logindata", section.getData());
        request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
    }

    private static void onSignUp(HttpServletRequest request,
                                 HttpServletResponse response)
            throws ServletException, IOException {

        Optional<String> s_username = ofNullable(request.getParameter("username"));
        Optional<String> s_email = ofNullable(request.getParameter("email"));
        Optional<String> s_password = ofNullable(request.getParameter("password"));
        Optional<String> s_password_confirm = ofNullable(request.getParameter("password_confirm"));

        if(s_username.isPresent() && s_email.isPresent() &&
                s_password.isPresent() && s_password_confirm.isPresent() &&
                s_password.get().equals(s_password_confirm.get())){

            String[] signUpData = {s_username.get(),s_email.get(), s_password.get()};
            doSignUp(request, response, signUpData);

        } else {
            String message = "Provided information is not valid, please try again!";
            forwardToSignUp(request,response,message);
        }
    }

    private static void doSignUp(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String[] signUpData) throws ServletException, IOException {
        String username = signUpData[0];
        String email = signUpData[1];
        String password = signUpData[2];

        try {
            Optional<User> o_user = userDao.getUserByLogin(username);
            if(o_user.isPresent())
                forwardToSignUp(request, response,"Username is already taken!");
            else {
                User user = (new User())
                        .setLogin(username)
                        .setAccessLevel(3)
                        .setEmail(email)
                        .setPassword(encode(password));
                user = userDao.addUser(user);
                UserProfile profile = (new UserProfile())
                        .setUserid(user.getId())
                        .setEmail(email)
                        .setName("New")
                        .setLastName("User");
                profile = profileDao.addUserProfile(profile);
                request.getSession().setAttribute("profile", profile);
                onLoginSuccess(request,response,user);
                response.sendRedirect(request.getContextPath() + "/welcome"); // Go to welcome page.
            }
        } catch (DaoException e) {
            logger.warn(e.getMessage(),e);
            forwardToSignUp(request, response,"Something went wrong =(");
        }
    }

    private static void forwardToSignUp(HttpServletRequest request,
                                        HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("errormessage", message);
        SectionModule section = sections.get("signup");
        request.setAttribute("section", section);
        request.setAttribute("logindata", section.getData());
        request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
    }

    private static void onLoginSuccess(HttpServletRequest request,
                                       HttpServletResponse response, User user)
                                                    throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("user", user); // Put user in a session.
        logger.info("User "+user.getLogin()+" successfully logged in.");
        tokensDao.addToken(user);
        Cookie loginCookie = new Cookie("token", encode(user.getLogin()));
        response.addCookie(loginCookie);
    }

    private static void recoverPass(HttpServletRequest request,
                                    HttpServletResponse response)
            throws ServletException, IOException {

    }

    private static String encode(String password) {
        return MD5Encoder.encode(ConcurrentMessageDigest.digestMD5(password.getBytes()));
    }

    public static void setUserDao(UserDao userDao) {
        LoginService.userDao = userDao;
    }

    public static void setProfileDao(UserProfileDao profileDao) {
        LoginService.profileDao = profileDao;
    }

    public static void setTokensDao(TokensDao tokensDao) {
        LoginService.tokensDao = tokensDao;
    }
}
