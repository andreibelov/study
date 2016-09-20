package ru.andrw.java.jsonchat.servlet;

import ru.andrw.java.jsonchat.dao.*;
import ru.andrw.java.jsonchat.model.chat.ChatMessage;
import ru.andrw.java.jsonchat.model.User;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;

import java.io.IOException;

/**
 * Created by john on 7/3/2016.
 */
@WebServlet(name = "Chat", urlPatterns = {"/chat"})
public class Chat extends javax.servlet.http.HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream input = request.getInputStream();
        DAO chat; User user;
        //Read the request
        try {
            chat = DaoFactory.getDao();

            ChatMessage message = (ChatMessage) JAXBContext
                    .newInstance(ChatMessage.class)
                    .createUnmarshaller()
                    .unmarshal(request.getInputStream());
            message.setSender(((User) request.getSession().getAttribute("user")).toString());

            chat.addMessage(message);

        } catch (Exception e) { /*report an error*/ } catch (DAOException e) {
            e.printStackTrace();
        }

    }

}
