package ru.andrw.java.socialnw.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by john on 10/6/2016.
 * ErrorHandler servlet based on
 * http://stackoverflow.com/questions/26801038/
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet("/error")
public class Errors extends HttpServlet {

    private static final Logger logger = LoggerFactory
            .getLogger(Errors.class);

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }
    private void processError(HttpServletRequest request,
                              HttpServletResponse response) throws IOException, ServletException {
        //customize error message
        Throwable throwable = (Throwable) request
                .getAttribute("javax.servlet.error.exception");
        String requestUri = (String) request
                .getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }
        if(throwable != null){
            logger.error(requestUri,throwable);
        }

        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");

        String servletName = (String) request
                .getAttribute("javax.servlet.error.servlet_name");
        if (servletName == null) {
            servletName = "Unknown";
        }


        request.getRequestDispatcher("/WEB-INF/jsp/error-page.jsp").forward(request, response);
    }
}