package ru.andrw.java.jsonchat.servlet;

import ru.andrw.java.jsonchat.dao.DAO;
import ru.andrw.java.jsonchat.dao.DAOException;
import ru.andrw.java.jsonchat.dao.DaoFactory;
import ru.andrw.java.jsonchat.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by john on 7/3/2016.
 */
@WebServlet(name = "Chat", urlPatterns = {"/chat"})
public class Chat extends javax.servlet.http.HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

    }

}
