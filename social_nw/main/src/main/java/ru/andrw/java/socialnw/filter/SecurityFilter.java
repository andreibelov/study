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

/**
 * Created by john on 9/25/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/*"})
public class SecurityFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    private FilterConfig filterConfig;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        String uri = httpServletRequest.getRequestURI()
                .substring(httpServletRequest.getContextPath().length());
        MDC.put(Constants.SESSION_ID_MDC_KEY, session.getId());
        MDC.put("ip",request.getRemoteAddr());
        updateMDCValues(session);
        if (session.getAttribute("user") != null ||
                uri.startsWith("/static/") ||
                uri.equals("/login")) chain.doFilter(request, response);
        else {
            // Not logged in, show login page.
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login");
        }
        MDC.clear();

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException{
        this.filterConfig = filterConfig;
        logger.info("SecurityFilter initialised");
    }

    @Override
    public void destroy(){
        filterConfig = null;
        logger.info("SecurityFilter destroyed");
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
