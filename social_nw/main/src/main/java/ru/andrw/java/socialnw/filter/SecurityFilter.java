package ru.andrw.java.socialnw.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import ru.andrw.java.socialnw.util.Constants;

/**
 * Created by john on 9/25/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/*"})
public class SecurityFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    private FilterConfig filterConfig;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();

        MDC.put(Constants.SESSION_ID_MDC_KEY, session.getId());
        updateMDCValues(session);
        if (session.getAttribute("user") != null) chain.doFilter(request, response);
        else {
            session.setAttribute(Constants.USERID_SESSION_KEY, "anonimous"); // Put userid in a session.
            session.setAttribute(Constants.EMAIL_SESSION_KEY, "anonimous"); // Put email in a session.
            String uri = httpServletRequest.getRequestURI()
                    .substring(httpServletRequest.getContextPath().length());
            if (uri.equals("/login")) chain.doFilter(request,response);
            else if(uri.startsWith("/static/")) request.getServletContext()
                    .getNamedDispatcher("default").forward(request, response);
            else httpServletResponse // Not logged in, show login page.
                        .sendRedirect(httpServletRequest.getContextPath() + "/login");
        }
        MDC.clear();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException{
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy(){
        filterConfig = null;
    }

    private void updateMDCValues(HttpSession session) {
        updateMDCSingleValue(session, Constants.USERID_SESSION_KEY, Constants.USERID_MDC_KEY);
        updateMDCSingleValue(session, Constants.EMAIL_SESSION_KEY, Constants.EMAIL_MDC_KEY);
    }

    private void updateMDCSingleValue(HttpSession session, String sessionKey, String mdcKey) {
        String val = (String)session.getAttribute(sessionKey);
        MDC.put(mdcKey, val);
    }

}
