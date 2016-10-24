package ru.andrw.java.socialnw.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by john on 10/23/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class BageTag extends TagSupport {


    /**
     * Processing of the start tag that writes data to the
     * JspWriter's buffer or, if no buffer is used,
     * directly to the underlying writer.
     * @return SKIP_BODY
     */
    @Override
    public int doStartTag() throws JspException {

        return SKIP_BODY;
    }
}
