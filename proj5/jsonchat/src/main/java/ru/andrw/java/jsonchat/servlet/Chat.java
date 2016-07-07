package ru.andrw.java.jsonchat.servlet;

import ru.andrw.java.jsonchat.dao.*;
import ru.andrw.java.jsonchat.model.ChatMessage;
import ru.andrw.java.jsonchat.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by john on 7/3/2016.
 */
@WebServlet(name = "Chat", urlPatterns = {"/chat"})
public class Chat extends javax.servlet.http.HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        DAO chat; User user;
        //Read the request
        try {
            chat = DaoFactory.getDao();

            ChatMessage message = (ChatMessage) JAXBContext
                    .newInstance(ChatMessage.class)
                    .createUnmarshaller()
                    .unmarshal(request.getInputStream());
            message.setCreator((User) request.getSession().getAttribute("user"));

            chat.addMessage(message);

        } catch (Exception e) { /*report an error*/ } catch (DAOException e) {
            e.printStackTrace();
        }

    }

}
