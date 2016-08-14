package com.example.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by john on 8/14/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebFilter(urlPatterns = "*.jsp", dispatcherTypes = {DispatcherType.INCLUDE})
public class LoggingFilter extends HttpFilter{

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {
        System.out.println("This is a text in Main servlet."+
                "\n <br/>Method: "+request.getMethod()+
                "\n <br/>request_uri: "+request.getAttribute("javax.servlet.include.request_uri")+
                "\n <br/>context_path: "+request.getAttribute("javax.servlet.include.context_path")+
                "\n <br/>servlet_path: "+request.getAttribute("javax.servlet.include.servlet_path")+
                "\n <br/>path_info: "+request.getAttribute("javax.servlet.include.path_info")+
                "\n <br/>query_string: "+request.getAttribute("javax.servlet.include.query_string")
        );
        chain.doFilter(request,response);
    }
}
