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
 * Created by john on 10/5/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebFilter(filterName = "StaticFilter", urlPatterns = {"/static/*"}, dispatcherTypes = {DispatcherType.INCLUDE})
public class StaticFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(StaticFilter.class);

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException{
        this.filterConfig = filterConfig;
        logger.info("StaticFilter initialised");
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        request.getServletContext()
                .getNamedDispatcher("default").forward(request, response);

    }

    @Override
    public void destroy(){
        logger.info("StaticFilter destroyed");
        filterConfig = null;
    }
}
