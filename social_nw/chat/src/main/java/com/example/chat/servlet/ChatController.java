package com.example.chat.control;

import com.example.chat.dao.ChatRoomDao;
import com.example.chat.dao.DaoFactory;
import com.example.chat.dao.MessageDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by john on 8/12/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet(name = "ChatServlet", urlPatterns = {"/chat"})
public class ChatController extends HttpServlet {

    private ChatRoomDao chatRoomDao;
    private MessageDao messageDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext sc = config.getServletContext();
        DaoFactory daoFactory = (DaoFactory) sc.getAttribute("daoFactory");
        chatRoomDao = daoFactory.getChatDao();
        messageDao = daoFactory.getMessageDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        String line = null;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null)
                sb.append(line);
        } catch (Exception e) { /*report an error*/ e.printStackTrace(); }
    }
}
