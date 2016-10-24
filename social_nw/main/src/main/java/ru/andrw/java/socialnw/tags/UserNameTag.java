package ru.andrw.java.socialnw.tags;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import javax.servlet.jsp.tagext.TagSupport;

import ru.andrw.java.socialnw.dao.DaoFactory;
import ru.andrw.java.socialnw.dao.UserProfileDao;
import ru.andrw.java.socialnw.model.auth.User;
import ru.andrw.java.socialnw.model.Profile;

/**
 * Created by john on 10/2/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class UserNameTag extends TagSupport {

    /**
     * Custom tag attribute
     */
    private boolean doLink = false;

    /**
     * @param doLink if User Name should be encapsulated with link
     */
    public void setDoLink(Boolean doLink) {
        this.doLink = doLink;
    }

    private static final Logger logger = LoggerFactory
            .getLogger("ru.andrw.java.socialnw.tags.CustomTag");

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

        DaoFactory daoFactory = (DaoFactory) sc.getAttribute("daoFactory");
        UserProfileDao profileDao = daoFactory.getProfileDao();

        Optional<Profile> o_profile = Optional.empty();
        Optional<User> user = Optional.ofNullable((User) session.getAttribute("user"));
        if (user.isPresent())
        o_profile = profileDao.getUserProfileById(user.get().getId());
        StringBuffer s_out = new StringBuffer();
        if (o_profile.isPresent()){
            Profile profile = o_profile.get();
            s_out.append(doLink?"<a href=\""+request.getContextPath()+"/profile?id="+profile.getId()+"\">":" ")
                    .append(o_profile.get().getFirstName())
                    .append(" ")
                    .append(o_profile.get().getLastName())
                    .append(doLink?"</a>":" ");
        } else {
            s_out.append("DELETED DELETED");
        }
        try {
            out.print(s_out);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return SKIP_BODY;
    }
}
