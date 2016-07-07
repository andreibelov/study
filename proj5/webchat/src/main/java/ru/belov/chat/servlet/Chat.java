package ru.belov.chat.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import java.io.IOException;

/**
 * Created by john on 7/7/2016.
 */

@WebServlet(name = "Chat", urlPatterns = {"/chat"})
public class Chat extends javax.servlet.http.HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

    }
}
