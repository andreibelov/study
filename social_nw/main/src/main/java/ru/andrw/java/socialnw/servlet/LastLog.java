package ru.andrw.java.socialnw.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.html.HTMLLayout;
import ch.qos.logback.classic.html.UrlCssBuilder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.read.CyclicBufferAppender;

import static ru.andrw.java.socialnw.filter.Constants.CYCLIC_BUFFER_APPENDER_NAME;


/**
 * Created by john on 10/3/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet(name = "ViewLastLogServlet", urlPatterns = {"/lastlog"})
public class LastLog extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(LastLog.class);

    private CyclicBufferAppender<ILoggingEvent> cyclicBufferAppender;
    private HTMLLayout layout;

    @Override
    public void init() throws ServletException {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        initialize(lc);
        super.init();
    }

    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter output = resp.getWriter();
        java.lang.reflect.Method method = null;
//        output.append(layout.getFileHeader());
        output.append("<table class=\"table table-striped\">")
                .append("<thead>");
        try {
            StringBuilder sbuf = new StringBuilder();
            method = layout.getClass().getSuperclass()
                    .getDeclaredMethod("buildHeaderRowForTable", StringBuilder.class);
            method.setAccessible(true);
            method.invoke(layout, sbuf);
            output.append(sbuf.toString());
        } catch (SecurityException | NoSuchMethodException |
                InvocationTargetException | IllegalAccessException e) {
            logger.error(e.getMessage(),e);
        }
        output.append("</thead><tbody>");
        printLogs(output);
        output.append("</tbody></table>")
                .append("<a name=\"bottom\" />");
//        output.append(layout.getFileFooter());
        output.flush();
        output.close();
    }

    private void printLogs(PrintWriter output) {
        int count = -1;
        if (cyclicBufferAppender != null) {
            count = cyclicBufferAppender.getLength();
        }

        if (count == -1) {
            output.append("<tr><td>Failed to locate CyclicBuffer</td></tr>\r\n");
        } else if (count == 0) {
            output.append("<tr><td>No logging events to display</td></tr>\r\n");
        } else {
            LoggingEvent le;
            for (int i = 0; i < count; i++) {
                le = (LoggingEvent) cyclicBufferAppender.get(i);
                output.append(layout.doLayout(le)).append("\r\n");
            }
        }
    }

    private void initialize(LoggerContext context) {
        logger.debug("Initializing ViewLastLog Servlet");
        cyclicBufferAppender = (CyclicBufferAppender<ILoggingEvent>) context
                .getLogger("ru.andrw.java.socialnw")
                .getAppender(CYCLIC_BUFFER_APPENDER_NAME);

        layout = new HTMLLayout();
        layout.setContext(context);
        UrlCssBuilder cssBuilder = new UrlCssBuilder();
        cssBuilder.setUrl("/main/static/css/pk.css");
        layout.setCssBuilder(cssBuilder);
//        layout.setPattern("%d%thread%level%logger{25}%mdc{"
//                + Constants.USERID_MDC_KEY + "}%msg");
        layout.setPattern("%d%thread%level%logger{25}%mdc{ip}%msg");
        layout.setTitle("Last Logging Events");
        layout.start();
    }

    public boolean isResetResistant() {
        return false;
    }

    public void onStop(LoggerContext arg0) {
    }

}
