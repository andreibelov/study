package com.example.chat.filter;

import com.example.chat.dao.DaoFactory;
import com.example.chat.dao.UserDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Pattern;

import static java.util.Optional.ofNullable;

/**
 * Created by john on 8/14/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/*"})
public class SecurityFilter implements Filter {

    Logger logger = LoggerFactory.getLogger("com.example.chat.filter.SecurityFilter");
    private FilterConfig filterConfig;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        String uri = httpServletRequest.getRequestURI()
                .substring(httpServletRequest.getContextPath().length());
        if (session.getAttribute("user") != null ||
                uri.startsWith("/static/") ||
                uri.equals("/login")) chain.doFilter(request, response);
        else {
            // Not logged in, show login page.
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login");
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
