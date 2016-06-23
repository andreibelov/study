package ru.belov.javax.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by john on 6/23/2016.
 */

@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/secured/*"})
public class SecurityFilter implements Filter{
    private FilterConfig filterConfig;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (((HttpServletRequest) request).getSession().getAttribute("user") != null) {
            chain.doFilter(request, response); // User is logged in, just continue request.
        } else {
            ((HttpServletResponse) response).sendRedirect("/login.jsp"); // Not logged in, show login page.
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException{
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy(){
        filterConfig = null;
    }

}