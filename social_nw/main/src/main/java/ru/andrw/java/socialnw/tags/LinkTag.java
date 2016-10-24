package ru.andrw.java.socialnw.tags;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import ru.andrw.java.socialnw.dao.UserProfileDao;

import static javax.servlet.jsp.tagext.Tag.SKIP_BODY;

/**
 * Created by john on 10/23/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class LinkTag extends TagSupport {

    private UserProfileDao profileDao;
    private final Logger logger = LoggerFactory
            .getLogger(LinkTag.class);

    /**
     * Processing of the start tag that writes data to the
     * JspWriter's buffer or, if no buffer is used,
     * directly to the underlying writer.
     * @return SKIP_BODY
     */
    @Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();
        HttpSession session = pageContext.getSession();
        ServletContext sc = pageContext.getServletContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();


        return SKIP_BODY;
    }
}
