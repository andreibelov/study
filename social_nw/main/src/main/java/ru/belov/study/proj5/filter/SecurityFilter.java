package ru.belov.study.proj5.filter;

import ru.belov.study.proj5.dao.DaoException;
import ru.belov.study.proj5.dao.UserDao;
import ru.belov.study.proj5.model.account.User;
import ru.belov.study.proj5.model.chat.ChatMessage;
import ru.belov.study.proj5.model.chat.ChatUser;
import ru.belov.study.proj5.service.ChatQueue;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Deque;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import static java.util.Optional.ofNullable;

/**
 * Created by john on 7/22/2016.
 */
@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/*"})
public class SecurityFilter implements Filter {

    private Pattern allowed;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String tmp = "(^\\/login.{1,100})"; // TODO Put this into filterConfig
        allowed = Pattern.compile(tmp);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);

    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (ofNullable(request.getSession().getAttribute("user")).isPresent()
                || request.getRequestURI().substring(request.getContextPath().length()).matches(allowed.pattern()) ) {
            String path = request.getRequestURI().substring(request.getContextPath().length());

            if (path.startsWith("/static/")) {
                chain.doFilter(request, response); // Goes to default servlet.
            } else if (path.equals("/login.do"))
                request.getRequestDispatcher("/login.do").forward(request, response);
            else {
                request.getRequestDispatcher("/jsp" + path).forward(request, response); // Goes to your controller.
            } // User is logged in or wants to access allowed space, just continue request.
        }else response.sendRedirect(request.getContextPath() + "/login2.jsp"); // Not logged in, show login page.}

    }
}

