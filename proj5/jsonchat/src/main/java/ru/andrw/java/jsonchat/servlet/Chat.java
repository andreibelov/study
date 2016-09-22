package ru.andrw.java.jsonchat.servlet;

import com.google.gson.Gson;
import ru.andrw.java.jsonchat.dao.*;
import ru.andrw.java.jsonchat.model.chat.ChatMessage;
import ru.andrw.java.jsonchat.model.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by john on 7/3/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet(name = "Chat", urlPatterns = {"/chat"})
public class Chat extends HttpServlet {

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
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream input = request.getInputStream();

        User user = (User) request.getSession().getAttribute("user");

        StringBuilder sb = new StringBuilder();
        String line = null;
        try (BufferedReader reader = request.getReader()) { //Read the request
            while ((line = reader.readLine()) != null)
                sb.append(line);
        } catch (Exception e) { /*report an error*/ }

        try {
            ChatMessage message = gson.fromJson(sb.toString(),ChatMessage.class);
            message.setUuid(UUID.randomUUID());
            message.setSender(user.getLogin());
            messageDao.createMessage(message);
        } catch (DaoException e) {
            e.printStackTrace();
            /*report an error*/
        }

    }

}
