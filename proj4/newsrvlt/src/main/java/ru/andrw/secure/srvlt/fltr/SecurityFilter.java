package ru.andrw.secure.srvlt.fltr;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by john on 6/24/2016.
 *
 */

@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/*"})
public class SecurityFilter implements Filter {
    private FilterConfig filterConfig;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (httpServletRequest.getSession().getAttribute("user") != null) {
            chain.doFilter(request, response); // User is logged in, just continue request.
        } else {
            String uri = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());
            if (uri.equals("/login")) chain.doFilter(request,response);
            else if (!uri.startsWith("/login.jsp"))
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.jsp"); // Not logged in, show login page.
            else chain.doFilter(request,response);
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
