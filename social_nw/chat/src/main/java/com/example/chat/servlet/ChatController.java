package com.example.chat.servlet;

import com.example.chat.dao.ChatMessageDao;
import com.example.chat.dao.DaoException;
import com.example.chat.dao.DaoFactory;
import com.example.chat.model.User;
import com.example.chat.model.chat.ChatMessage;

import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by john on 8/12/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet(name = "ChatServlet", urlPatterns = {"/chat"})
public class ChatController extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private ChatMessageDao messageDao;
    private Gson gson;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext sc = config.getServletContext();
        DaoFactory daoFactory =   (DaoFactory) sc.getAttribute("daoFactory");
        messageDao = daoFactory.getChatMessageDao();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/chat.jsp").include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream input = request.getInputStream();

        User user = (User) request.getSession().getAttribute("user");

        StringBuilder sb = new StringBuilder();
        String line = null;
        try (BufferedReader reader = request.getReader()) { //Read the request
            while ((line = reader.readLine()) != null)
                sb.append(line);
        } catch (Exception e) { logger.error(e.getMessage(),e); }

        try {
            ChatMessage message = gson.fromJson(sb.toString(),ChatMessage.class);
            message.setUuid(UUID.randomUUID());
            message.setSender(user.getLogin());
            messageDao.createMessage(message);
        } catch (DaoException e) {
            logger.error(e.getMessage(),e);
        }

    }

}