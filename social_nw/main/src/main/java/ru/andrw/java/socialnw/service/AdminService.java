package ru.andrw.java.socialnw.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.andrw.java.socialnw.model.Section;

import static java.util.Optional.ofNullable;

/**
 * Created by john on 10/3/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class AdminService {

    private static final Logger logger = LoggerFactory
            .getLogger("ru.andrw.java.socialnw.service.AdminService");
    private static final Map<String, Section> sections = new HashMap<>();

    static {
        sections.put("profiles",(new Section())
                .setSectionName("Profiles")
                .setCssFile("empty.css")
                .setJsFile("profiles.js")
                .setJspFile("profiles-table.jsp")
        );
        sections.put("reports",(new Section())
                .setSectionName("Reports")
                .setCssFile("logback.css")
                .setJsFile("reports.js")
                .setJspFile("reports.jsp")
        );
        sections.put("analytics",(new Section())
                .setSectionName("Analytics")
                .setCssFile("analytics.css")
                .setJsFile("analytics.js")
                .setJspFile("analytics.jsp")
        );
        sections.put("export",(new Section())
                .setSectionName("Export")
                .setCssFile("export.css")
                .setJsFile("export.js")
                .setJspFile("export.jsp")
        );
    }

    public static void doAction(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {
        Optional<String> s_section = ofNullable(request.getParameter("sec"));
        if(s_section.isPresent()) {
            request.setAttribute("section", sections.get(s_section.get()));
        } else {
            request.setAttribute("admin_default", "admin_default");
            request.setAttribute("section", sections.get("profiles"));
        }
        String nextJSP = "/WEB-INF/jsp/dashboard.jsp";
        request.setAttribute("pageTitle", "Admin Console");
        request.setAttribute("sections", sections.entrySet());
        request.getRequestDispatcher(nextJSP).include(request, response);
    }

}
