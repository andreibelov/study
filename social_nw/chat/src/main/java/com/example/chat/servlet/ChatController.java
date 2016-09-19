package com.example.chat.servlet;

import com.example.chat.dao.ChatRoomDao;
import com.example.chat.dao.DaoFactory;
import com.example.chat.dao.MessageDao;
import com.example.chat.dao.impl.PgDaoFactory;
import com.example.chat.model.JsonPack;
import com.example.chat.model.Message;
import com.example.chat.model.User;
import com.example.chat.util.DbUtil;
import com.google.gson.Gson;

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
import java.util.Date;
import java.util.UUID;

/**
 * Created by john on 8/12/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet(name = "ChatServlet", urlPatterns = {"/chat"})
public class ChatController extends HttpServlet {

    private ChatRoomDao chatRoomDao;
    private MessageDao messageDao;
    private Gson gson;

    @Override
    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//        ServletContext sc = config.getServletContext();
//        DaoFactory daoFactory = (DaoFactory) sc.getAttribute("daoFactory");
        DaoFactory daoFactory = new PgDaoFactory(new DbUtil());
        chatRoomDao = daoFactory.getChatDao();
        messageDao = daoFactory.getMessageDao();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuilder sb = new StringBuilder();
        String line = null;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null)
                sb.append(line);
        } catch (Exception e) { /*report an error*/ e.printStackTrace(); }
        JsonPack income = gson.fromJson(sb.toString(), JsonPack.class);
        HttpSession currentSession = request.getSession(false);
        User user = (User) currentSession.getAttribute("user");
        if(request.getParameter("action").equals("add")){
            Message message = new Message()
                    .setText(request.getParameter("text"))
                    .setTimestamp(new Date())
                    .setChatRoomId(Integer.parseInt(request.getParameter("chat_room_is")))
                    .setFromUserId(user.getUserid())
                    .setUuid(UUID.randomUUID());
            JsonPack pack = new JsonPack()
                    .setMessage(messageDao.addMessage(message));
            response.getWriter().print(gson.toJson(pack, JsonPack.class));
        }

    }
}
