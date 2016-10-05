package ru.andrw.java.socialnw.servlet;

import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.spi.LoggerContextListener;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.helpers.Transform;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.status.InfoStatus;
import ch.qos.logback.core.status.Status;
import ch.qos.logback.core.status.StatusListener;
import ch.qos.logback.core.status.StatusManager;
import ch.qos.logback.core.util.CachingDateFormatter;

import static ch.qos.logback.core.CoreConstants.LINE_SEPARATOR;
import static java.util.Optional.ofNullable;

/**
 * Created by john on 10/2/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet(name = "LogBackServlet", urlPatterns = {"/logback"})
public class LogBack extends HttpServlet {

    private static CachingDateFormatter SDF = new CachingDateFormatter("yyyy-MM-dd HH:mm:ss");
    private LoggerContext lc;
    private int count;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext sc = config.getServletContext();
        lc = (LoggerContext) LoggerFactory.getILoggerFactory();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        count = 0;
        List<LoggerContextListener> listener = lc.getCopyOfListenerList();
        StatusManager sm = lc.getStatusManager();
        Optional<String> s_submit = ofNullable(req.getParameter("action"));
        if (s_submit.isPresent() && "clear".equalsIgnoreCase(s_submit.get())) {
            sm.clear();
            sm.add(new InfoStatus("Cleared all status messages", this));
        }

        resp.setContentType("text/html");
        PrintWriter output = resp.getWriter();

        if(s_submit.isPresent())
            output.append("<div class=\"alert alert-danger\" role=\"alert\">")
                .append("<strong>")
                .append("All status messages cleared!")
                .append("</strong></div>");

        output.append("<h2>Status messages for LoggerContext named ")
                .append(lc.getName())
                .append(" <a href=\"#\"><i id=\"fa-refresh\" class=\"fa fa-refresh\" aria-hidden=\"true\"></i></a>")
                .append(" <a href=\"#\" id=\"clear\">\n" +
                        "        <span class=\"glyphicon glyphicon-trash\"></span>\n" +
                        "    </a></h2>\r\n");

        output.append("<table>");
        StringBuilder buf = new StringBuilder();
        if (sm != null) {
            printList(buf, sm);
        } else {
            output.append("Could not find status manager");
        }
        output.append(buf)
                .append("</table>");
        output.flush();
        output.close();
    }

    private void printList(StringBuilder buf, StatusManager sm) {
        buf.append("<table>\r\n");
        printHeader(buf);
        List<Status> statusList = sm.getCopyOfStatusList();
        for (Status s : statusList) {
            count++;
            printStatus(buf, s);
        }
        buf.append("</table>\r\n");
    }

    private void printHeader(StringBuilder buf) {
        buf.append("  <tr class=\"header\">\r\n");
        buf.append("    <th>Date </th>\r\n");
        buf.append("    <th>Level</th>\r\n");
        buf.append("    <th>Origin</th>\r\n");
        buf.append("    <th>Message</th>\r\n");
        buf.append("  </tr>\r\n");

    }

    private String statusLevelAsString(Status s) {
        switch (s.getEffectiveLevel()) {
            case Status.INFO:
                return "INFO";
            case Status.WARN:
                return "<span class=\"warn\">WARN</span>";
            case Status.ERROR:
                return "<span class=\"error\">ERROR</span>";
        }
        return null;
    }

    private String abbreviatedOrigin(Status s) {
        Object o = s.getOrigin();
        if (o == null) {
            return null;
        }
        String fqClassName = o.getClass().getName();
        int lastIndex = fqClassName.lastIndexOf(CoreConstants.DOT);
        if (lastIndex != -1) {
            return fqClassName.substring(lastIndex + 1, fqClassName.length());
        } else {
            return fqClassName;
        }
    }

    private void printStatus(StringBuilder buf, Status s) {
        String trClass;
        if (count % 2 == 0) {
            trClass = "even";
        } else {
            trClass = "odd";
        }
        buf.append("  <tr class=\"").append(trClass).append("\">\r\n");
        String dateStr = SDF.format(s.getDate());
        buf.append("    <td class=\"date\">").append(dateStr).append("</td>\r\n");
        buf.append("    <td class=\"level\">").append(statusLevelAsString(s)).append("</td>\r\n");
        buf.append("    <td>").append(abbreviatedOrigin(s)).append("</td>\r\n");
        buf.append("    <td>").append(s.getMessage()).append("</td>\r\n");
        buf.append("  </tr>\r\n");
        if (s.getThrowable() != null) {
            printThrowable(buf, s.getThrowable());
        }
    }

    private void printThrowable(StringBuilder buf, Throwable t) {
        buf.append("  <tr>\r\n");
        buf.append("    <td colspan=\"4\" class=\"exception\"><pre>");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        buf.append(Transform.escapeTags(sw.getBuffer()));
        buf.append("    </pre></td>\r\n");
        buf.append("  </tr>\r\n");

    }
}
