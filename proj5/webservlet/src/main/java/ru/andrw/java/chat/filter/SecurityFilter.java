package ru.andrw.java.chat.filter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.regex.Pattern;

import static java.util.Optional.ofNullable;

/**
 * Created by john on 7/1/2016.
 * This Security Filter prevents user to access
 */

public class SecurityFilter extends HttpFilter {
    private FilterConfig filterConfig;
    private Pattern allowed = Pattern.compile("(^\\/login.{1,100})"); // TODO Put this into filterConfig

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig =  filterConfig;
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
            IOException, ServletException {
        if (ofNullable(request.getSession().getAttribute("user")).isPresent()
                || request.getRequestURI().substring(request.getContextPath().length()).matches(allowed.pattern()) )
            chain.doFilter(request, response); // User is logged in or wants to access allowed space, just continue request.
        else response.sendRedirect(request.getContextPath() + "/login.jsp"); // Not logged in, show login page.}

    }

    @Override
    public void destroy() {filterConfig = null;}
}
