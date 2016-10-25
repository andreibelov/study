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
import ru.andrw.java.socialnw.dao.FriendsDao;
import ru.andrw.java.socialnw.model.auth.User;

/**
 * Created by john on 10/23/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class RelationTag extends TagSupport {

    private static final Logger logger = LoggerFactory
            .getLogger(RelationTag.class);

    /**
     * Custom tag attribute
     */
    private Long testId;
    /**
     * @param testId of user with whom testing a relation
     */
    public void setTestId(Long testId) {
        this.testId = testId;
    }
    private String[] buttons1 = {
            "<button class=\"btn btn-md btn-primary btn-block befriendly\" data-target=\"",
            "<button class=\"btn btn-md btn-default btn-block unfriend\" data-target=\"",
            "<button class=\"btn btn-md btn-success btn-block befriendly\" data-target=\"",
            "<button class=\"btn btn-md btn-default btn-block unfriend\" data-target=\"",
            "<button class=\"btn btn-md btn-info btn-block unblock\" data-target=\"",
            "<button class=\"btn btn-md btn-danger btn-block disabled\" disabled data-target=\"",
            "<button class=\"btn btn-md btn-warning btn-block unblock\" data-target=\"",
            "<button id=\"editProfile\" class=\"btn btn-md btn-default btn-block\" data-target=\"",
    };

    private String[] buttons2 = {
            "\">Add to friends</button>",
            "\">Unsend</button>",
            "\">Accept</button>",
            "\">Remove</button>",
            "\">Unblock</button>",
            "\">Blocked</button>",
            "\">Unblock</button>",
            "\">Edit</button>"

    };
    /**
     * Processing of the start tag that writes data to the
     * JspWriter's buffer or, if no buffer is used,
     * directly to the underlying writer.
     * @return SKIP_BODY
     */
    @Override
    public int doStartTag() throws JspException {

        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();

        JspWriter out = pageContext.getOut();

        HttpSession session = pageContext.getSession();
        ServletContext sc = pageContext.getServletContext();

        DaoFactory daoFactory = (DaoFactory) sc.getAttribute("daoFactory");
        FriendsDao friendsDao = daoFactory.getFriendsDao();

        int status;
        User user = (User) session.getAttribute("user");
        StringBuilder s_out = new StringBuilder();
        status = friendsDao.friendsStatus(user.getId(), testId);
        s_out.append(buttons1[status])
                .append(testId)
                .append(buttons2[status]);
        try {
            out.print(s_out.toString());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return SKIP_BODY;
    }
}
