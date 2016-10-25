package ru.andrw.java.socialnw.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static java.util.Optional.ofNullable;

/**
 * Created by john on 10/25/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebFilter(filterName = "LocaleFilterFilter", urlPatterns = {"/locale"})
public class LocaleFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(LocaleFilter.class);

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        logger.info("LocaleFilter initialised");
    }


    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        String language;
        Optional<String> o_lang =  ofNullable(request.getParameter("lang"));
        if(o_lang.isPresent()){
            language = o_lang.get();
        } else {
            Locale locale = request.getLocale();
            language = locale.getLanguage()+"_"+locale.getCountry();
        }
        session.setAttribute("language", language);
        chain.doFilter(request,response);
    }

    @Override
    public void destroy(){
        logger.info("LocaleFilter destroyed");
        filterConfig = null;
    }
}
