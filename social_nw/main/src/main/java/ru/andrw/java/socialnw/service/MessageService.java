package ru.andrw.java.socialnw.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.andrw.java.socialnw.dao.DaoException;
import ru.andrw.java.socialnw.dao.DaoFactory;
import ru.andrw.java.socialnw.dao.IMDao;
import ru.andrw.java.socialnw.dao.UserProfileDao;

/**
 * Created by john on 10/24/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
class MessageService {


    private static final Logger logger = LoggerFactory
            .getLogger(PostService.class);
    private static IMDao messageDao;


    static void init(DaoFactory daoFactory) throws ServletException, DaoException {
        messageDao = daoFactory.getIMDao();
    }


    static void getAction(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        PageBuilder.getDefault(request,response);
    }

    static void postAction(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException {
        PageBuilder.getDefault(request,response);
    }


}
