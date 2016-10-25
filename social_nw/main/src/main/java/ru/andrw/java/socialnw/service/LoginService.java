package ru.andrw.java.socialnw.service;

import org.apache.tomcat.util.security.ConcurrentMessageDigest;
import org.apache.tomcat.util.security.MD5Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.andrw.java.socialnw.dao.DaoException;
import ru.andrw.java.socialnw.dao.TokensDao;
import ru.andrw.java.socialnw.dao.UserDao;
import ru.andrw.java.socialnw.dao.UserProfileDao;
import ru.andrw.java.socialnw.model.view.Section;
import ru.andrw.java.socialnw.model.auth.User;
import ru.andrw.java.socialnw.model.Profile;
import ru.andrw.java.socialnw.service.ifaces.ServiceMethod;

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

    private static final Map<String, Section> sections = new ConcurrentHashMap<>();
    private static final Map<String, ServiceMethod> methods = new ConcurrentHashMap<>();
    private static UserProfileDao profileDao;
    private static UserDao userDao;
    private static TokensDao tokensDao;
    private static final String usernamePattern = "^[a-z0-9._]+$";
    private static final String emailPattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final String passwordPattern = "^[A-Za-z0-9.]{6,}$";

    static {
        sections.put("login", (new Section())
                .setSectionName("Login")
                .setPageTitle("Login")
                .setCssFile("login.css")
                .setJsFile("login.js")
                .setJspFile("forms/login-form.jsp")
                .setData("login")
        );
        sections.put("signup", (new Section())
                .setSectionName("Signup")
                .setPageTitle("Register")
                .setCssFile("login.css")
                .setJsFile("login.js")
                .setJspFile("forms/login-form.jsp")
                .setData("signup")
        );
        sections.put("recover", (new Section())
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

    public static void doGetAction(HttpServletRequest request,
                                   HttpServletResponse response)
            throws ServletException, IOException {
        Section section;
        Optional<String> s_action = ofNullable(request.getParameter("action"));
        section = (s_action.isPresent() && sections.containsKey(s_action.get())) ?
                sections.get(s_action.get()) : sections.get("login");
        request.setAttribute("section", section);
        request.setAttribute("logindata", section.getData());
        request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").include(request, response);

    }

    public static void doPostAction(HttpServletRequest request,
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

    static void onLogOut(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        //invalidate the session if exists
        HttpSession session = request.getSession(false);
        if(session != null){
            User user = (User) session.getAttribute("user");
            session.removeAttribute("user");
            Cookie loginCookie = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        loginCookie = cookie;
                        break;
                    }
                }
            }
            if (loginCookie != null) {
                loginCookie.setMaxAge(0);
                response.addCookie(loginCookie);
            }
            logger.info("User "+user.getLogin()+" successfully logged out.");
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() +"/login");
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
            response.sendRedirect(request.getContextPath() + "/news"); // Go to some start page.
        } else {
            logger.warn("Bad login try "+email);
            forwardToLogin(request, response, "Provided credentials are not valid, try again!");
        }

    }

    private static void forwardToLogin(HttpServletRequest request,
                                       HttpServletResponse response, String message)
                                    throws ServletException, IOException {
        request.setAttribute("errormessage", message); // Set error msg for ${error}
        Section section = sections.get("login");
        request.setAttribute("section", section);
        request.setAttribute("logindata", section.getData());
        request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
    }

    private static void onSignUp(HttpServletRequest request,
                                 HttpServletResponse response)
            throws ServletException, IOException {

        if(!validator(request)){
            String message = "Provided information is not valid, please try again!";
            forwardToSignUp(request,response,message);
        } else {
            doSignUp(request, response, (String[]) request.getAttribute("signUpData"));
        }
    }

    private static boolean validator(HttpServletRequest request){

        boolean flag = false;

        Optional<String> s_username = ofNullable(request.getParameter("username"));
        Optional<String> s_email = ofNullable(request.getParameter("email"));
        Optional<String> s_password = ofNullable(request.getParameter("password"));
        Optional<String> s_password_confirm = ofNullable(request.getParameter("password_confirm"));

        if(s_username.isPresent() && s_email.isPresent() &&
                s_password.isPresent() && s_password_confirm.isPresent()) {

            String[] signUpData = {
                    s_username.get().toLowerCase(),
                    s_email.get(),
                    s_password.get(),
                    s_password_confirm.get()
            };

            boolean a = signUpData[0].matches(usernamePattern);
            boolean b = signUpData[1].matches(emailPattern);
            boolean c = signUpData[2].matches(passwordPattern);
            boolean d = signUpData[3].equals(signUpData[2]);

            flag = a&&b&&c&&d;

            if(flag) request.setAttribute("signUpData", signUpData);
        }

        return flag;
    }

    private static void doSignUp(HttpServletRequest request,
                                 HttpServletResponse response,
                                 String[] signUpData) throws ServletException, IOException {
        String username = signUpData[0];
        String email = signUpData[1];
        String password = signUpData[2];

        try {
            if(userDao.getUserByLogin(username).isPresent())
                forwardToSignUp(request, response,"Username is already taken!");
            else if(userDao.getUserByEmail(email).isPresent()){
                forwardToSignUp(request, response,"User with email:"+email+" is already registered!");
            }
            else {
                Profile profile = (new Profile())
                        .setFirstName("New")
                        .setLastName("User");
                profile.setLogin(username)
                        .setEmail(email)
                        .setPassword(encode(password));
                profile = profileDao.regNewProfile(profile);
                request.getSession().setAttribute("profile", profile);
                onLoginSuccess(request,response,profile);
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
        Section section = sections.get("signup");
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
