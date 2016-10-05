package ru.andrw.java.socialnw.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.andrw.java.socialnw.model.SectionModule;
import ru.andrw.java.socialnw.servlet.Admin;

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
    private static final Map<String, SectionModule> sections = new HashMap<>();

    static {
        sections.put("profiles",(new SectionModule())
                .setSectionName("Profiles")
                .setCssFile("empty.css")
                .setJsFile("profiles.js")
                .setJspFile("profiles-table.jsp")
        );
        sections.put("reports",(new SectionModule())
                .setSectionName("Reports")
                .setCssFile("logback.css")
                .setJsFile("reports.js")
                .setJspFile("reports.jsp")
        );
        sections.put("analytics",(new SectionModule())
                .setSectionName("Analytics")
                .setCssFile("analytics.css")
                .setJsFile("analytics.js")
                .setJspFile("analytics.jsp")
        );
        sections.put("export",(new SectionModule())
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
        } else request.setAttribute("admin_default", "admin_default");
        String nextJSP = "/WEB-INF/jsp/dashboard.jsp";
        request.setAttribute("pageTitle", "Admin Console");
        request.setAttribute("sections", sections.entrySet());
        request.getRequestDispatcher(nextJSP).include(request, response);
    }

}
