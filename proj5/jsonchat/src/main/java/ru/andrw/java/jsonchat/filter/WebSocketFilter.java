package ru.andrw.java.jsonchat.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Created by john on 9/20/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebFilter("/websocket")
public class WebSocketFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        final Map<String, String[]> fakedParams = Collections.singletonMap("sessionId",
                new String[] { httpRequest.getSession().getId() });
        HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(httpRequest) {
            @Override
            public Map<String, String[]> getParameterMap() {
                return fakedParams;
            }
        };
        chain.doFilter(wrappedRequest, response);
    }

    @Override
    public void init(FilterConfig config) throws ServletException { }
    @Override
    public void destroy() { }
}
