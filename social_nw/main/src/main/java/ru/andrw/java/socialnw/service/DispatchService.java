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

import ru.andrw.java.socialnw.model.SectionModule;
import ru.andrw.java.socialnw.model.UserProfile;

import static java.util.Optional.ofNullable;

/**
 * Created by john on 10/9/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class DispatchService {

    private static final Logger logger = LoggerFactory.getLogger(DispatchService.class);
    private static final Map<String, SectionModule> sections = new ConcurrentHashMap<>();
    private static final Map<String, ServiceMethod> methods = new ConcurrentHashMap<>();

    static {
        sections.put("welcome", (new SectionModule())
                .setSectionName("Welcome")
                .setPageTitle("Welcome")
                .setCssFile("profile.css")
                .setJsFile("profile.js")
                .setJspFile("welcome.jsp")
        );
        sections.put("default", (new SectionModule())
                .setSectionName("News")
                .setPageTitle("Welcome back!")
                .setCssFile("news.css")
                .setJsFile("news.js")
                .setJspFile("news.jsp")
        );
        sections.put("inbox", (new SectionModule())
                .setSectionName("Inbox")
                .setPageTitle("Messages")
                .setCssFile("inbox.css")
                .setJsFile("inbox.js")
                .setJspFile("inbox.jsp")
        );
        sections.put("profile", (new SectionModule())
                .setSectionName("Profile")
                .setPageTitle("Profile")
                .setCssFile("profile.css")
                .setJsFile("profile.js")
                .setJspFile("profile.jsp")
        );
        methods.put("default", DispatchService::welcome);
    }

    public static void doDispatch(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException{

        String uri = request.getRequestURI()
                .substring(request.getContextPath().length()+1);

        Optional<String> sectionName = sections.keySet()
                .stream().filter(uri::startsWith).findAny();
        SectionModule section = sections.get("default");
        if(sectionName.isPresent()) section = sections.get(sectionName.get());
        request.setAttribute("section",section);
        request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
    }

    private static void welcome(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException{
        HttpSession session = request.getSession();
        String attrib = "profile";
        Optional<UserProfile> o_profile = ofNullable((UserProfile) session.getAttribute(attrib));

        if(o_profile.isPresent()) {
            session.removeAttribute(attrib);
            request.setAttribute(attrib, o_profile.get());
        }
    }
}
