package ru.andrw.java.jsonchat.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Created by john on 7/3/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class Logout extends HttpServlet {
}
