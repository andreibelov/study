package com.example.chat.filter;

import com.example.chat.dao.DaoFactory;
import com.example.chat.dao.UserDao;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
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
public class SecurityFilter extends HttpFilter {

    private Pattern allowed;
    private FilterConfig filterConfig;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        this.filterConfig = filterConfig;
        String tmp = "^\\/login(\\.jsp)?$|^\\/static\\/.*"; // TODO Put this into filterConfig
        allowed = Pattern.compile(tmp);

    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String path = request.getRequestURI().substring(request.getContextPath().length());

        if (ofNullable(session.getAttribute("user")).isPresent() || path.matches(allowed.pattern()) ) {
            chain.doFilter(request,response);
        } else response.sendRedirect(request.getContextPath() + "/login.jsp"); // Not logged in, show login page.}
    }

    @Override
    public void destroy(){
        filterConfig = null;
    }

}
