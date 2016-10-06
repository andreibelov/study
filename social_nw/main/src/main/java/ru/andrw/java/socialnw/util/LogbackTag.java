package ru.andrw.java.socialnw.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.ErrorData;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by john on 9/27/2016.
 * Definition of the custom JSTL tag for logging RTE
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class LogbackTag extends TagSupport {

    private static final Logger logger = LoggerFactory
            .getLogger(LogbackTag.class);
    private Exception exception;
    private ErrorData errorData;

    /**
     * @param ex is an exception occured
     */
    public void setException(Exception ex){this.exception=ex;}

    /**
     * @param eData error data to display in the log
     */
    public void setErrorData(ErrorData eData){this.errorData = eData;}

    /**
     * Processing of the start tag that writes list of users to the
     * JspWriter's buffer or, if no buffer is used, directly to the
     * underlying writer.
     * @return SKIP_BODY
     */
    @Override
    public int doStartTag() throws JspException {
        logger.error(exception.getMessage(), errorData.getThrowable());
        return SKIP_BODY;
    }
}
