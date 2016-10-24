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

import ru.andrw.java.socialnw.model.Profile;
import ru.andrw.java.socialnw.model.view.Section;

import static java.util.Optional.ofNullable;

/**
 * Created by john on 10/23/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
class PageBuilder {

    private static final Logger logger = LoggerFactory.getLogger(PageBuilder.class);
    private static final Map<String, Section> sections = new ConcurrentHashMap<>();

    static {
        sections.put("home", (new Section())
                .setData("<i class=\"fa fa-fw fa-home\"></i>")
                .setSectionName("Home")
                .setPageTitle("Welcome back!")
                .setCssFile("news.css")
                .setJsFile("news.js")
                .setJspFile("news.jsp")
        );
        sections.put("news", (new Section())
                .setData("<i class=\"fa fa-newspaper-o\" aria-hidden=\"true\"></i>\n")
                .setSectionName("News")
                .setPageTitle("News feed")
                .setCssFile("news.css")
                .setJsFile("news.js")
                .setJspFile("news.jsp")
        );
        sections.put("inbox", (new Section())
                .setData("<i class=\"fa fa-fw fa-envelope\"></i>")
                .setSectionName("Inbox")
                .setPageTitle("Messages")
                .setCssFile("inbox.css")
                .setJsFile("inbox.js")
                .setJspFile("inbox.jsp")
        );
        sections.put("profile", (new Section())
                .setData("<i class=\"fa fa-fw fa-user\"></i>")
                .setSectionName("Profile")
                .setPageTitle("Profile")
                .setCssFile("profile.css")
                .setJsFile("profile.js")
                .setJspFile("profile.jsp")
        );
        sections.put("friends", (new Section())
                .setData("<i class=\"fa fa-fw fa-users\" aria-hidden=\"true\"></i>")
                .setSectionName("Friends")
                .setPageTitle("Friends")
                .setCssFile("friends.css")
                .setJsFile("friends.js")
                .setJspFile("profiles.jsp")
        );
    }

    static void getDefault(HttpServletRequest request,
                                   HttpServletResponse response)
            throws ServletException, IOException {

        String uri = request.getRequestURI()
                .substring(request.getContextPath().length()+1);
        String sectionName = sections.keySet()
                .stream().filter(uri::equals).findAny().orElse("news");
        Section section = sections.get(sectionName);
        buildPage(request, response, section);
    }

    static void buildPage(HttpServletRequest request, HttpServletResponse response,
                                  Section section) throws ServletException, IOException {
        request.setAttribute("section",section);
        request.setAttribute("sections", sections.entrySet());
        request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
    }

    static void welcome(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException{
        HttpSession session = request.getSession();
        String attrib = "profile";
        Optional<Profile> o_profile = ofNullable((Profile) session.getAttribute(attrib));
        Section section = (new Section())
                .setSectionName("Welcome")
                .setPageTitle("Welcome")
                .setCssFile("profile.css")
                .setJsFile("profile.js")
                .setJspFile("welcome.jsp");
        if(o_profile.isPresent()) {
            session.removeAttribute(attrib);
            request.setAttribute(attrib, o_profile.get());
            request.setAttribute("action", "edit");
            PageBuilder.buildPage(request, response, section);
        } else response.sendRedirect(request.getContextPath() + "/news"); // Go to home page.
    }
}
