package ru.belov.study.proj5.filter;

import ru.belov.study.proj5.core.MatchItr;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.StreamSupport.stream;

/**
 * Created by john on 8/9/2016.
 */
@WebFilter(filterName = "IncludeFilter",
        urlPatterns = {"/jsp/include/*"},
        dispatcherTypes={DispatcherType.INCLUDE})
public class IncludeFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest) req;
        chain.doFilter(request,response);
//        String uri = request.getRequestURI().substring(request.getContextPath().length()).toLowerCase();
//        Matcher matcher = Pattern.compile("(\\p{L}{1,32}\\.jsp).*")
//                .matcher(uri);
//        System.out.println(uri);
//        Optional<String> jspPage = stream(new MatchItr(matcher), false)
//                .map(gs->gs[1].toLowerCase()) // TODO lookup for jsp's
//                .findAny();
//        if (jspPage.isPresent()){
//            request.getServletContext().getNamedDispatcher("jsp").include(request, response);
//        } else chain.doFilter(request, response);

    }
}
