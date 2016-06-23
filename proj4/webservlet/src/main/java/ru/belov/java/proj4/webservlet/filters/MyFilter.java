package ru.belov.java.proj4.webservlet.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter(filterName = "MyHttpFilter", urlPatterns = {"/*"})
public class MyFilter implements Filter{
    private FilterConfig filterConfig;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        String uri = req.getRequestURI();
        if(session == null && !(uri.endsWith("/login"))){
            res.sendRedirect(req.getContextPath()+"/login");
        } else if(session != null && uri.endsWith("/login")) {
            res.sendRedirect(req.getContextPath());
        } else {
            chain.doFilter(request, response);
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