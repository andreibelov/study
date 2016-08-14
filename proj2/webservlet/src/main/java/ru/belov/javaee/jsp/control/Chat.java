package ru.belov.javaee.jsp.control;

import com.google.gson.Gson;
import ru.belov.javaee.jsp.dao.DAOException;
import ru.belov.javaee.jsp.dao.impl.MessageDAOList;
import ru.belov.javaee.jsp.model.ChatUser;
import ru.belov.javaee.jsp.model.JsonText;
import ru.belov.javaee.jsp.model.Message;
import ru.belov.javaee.jsp.service.MsgQueue;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Deque;


/**
 * Created by john on 7/22/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet(name = "Chat", urlPatterns = {"/chat"})
public class Chat extends HttpServlet {
    private Gson gson;
    private MsgQueue queue;
    private MessageDAOList chatdao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext sc = config.getServletContext();
        gson =      (Gson) sc.getAttribute("gson");
        queue =     (MsgQueue) sc.getAttribute("msgQueue");
        chatdao =   (MessageDAOList) sc.getAttribute("chatdao");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        ChatUser chatUser = (ChatUser) session.getAttribute("chat_user");
        Deque<Message> messageDeque = null;
        try {
            messageDeque = (Deque<Message>) session.getAttribute("user_deque");
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        String line = null;
        try (BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null)
                sb.append(line);
        } catch (Exception e) { /*report an error*/ }
        JsonText jsonText = gson.fromJson(sb.toString(), JsonText.class);
        Message txt = null;
        try {
            txt = chatdao.create(jsonText.getText(), chatUser).setTimestamp(LocalDateTime.now());
        } catch (DAOException e) {
            e.printStackTrace();
        }

        queue.addNewMsg(txt);

        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();

        out.print(gson.toJson(messageDeque.pollLast(), Message.class));
        out.flush();
    }

}
