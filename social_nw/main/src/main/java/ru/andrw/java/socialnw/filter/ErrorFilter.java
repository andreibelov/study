package ru.andrw.java.socialnw.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Created by john on 10/6/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebFilter(filterName = "ErrorFilter",
        urlPatterns = {"/error"}, dispatcherTypes = {DispatcherType.INCLUDE})
public class ErrorFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(ErrorFilter.class);
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        logger.info("ErrorFilter initialised");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        logger.debug(request.getServletContext().getNamedDispatcher("jsp").toString());
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        filterConfig = null;
        logger.info("ErrorFilter destroyed");
    }
}
