package ru.andrw.java.socialnw.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by john on 9/25/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
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
            else if(uri.startsWith("/static/")) request.getServletContext().getNamedDispatcher("default").forward(request, response);
            else httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login"); // Not logged in, show login page.
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
