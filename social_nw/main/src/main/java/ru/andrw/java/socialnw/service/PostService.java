package ru.andrw.java.socialnw.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.andrw.java.socialnw.dao.DaoException;
import ru.andrw.java.socialnw.dao.DaoFactory;
import ru.andrw.java.socialnw.dao.UserProfileDao;

/**
 * Created by john on 10/10/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
class PostService{

    private static final Logger logger = LoggerFactory
            .getLogger(PostService.class);
    private static UserProfileDao profileDao;

    static {

    }


    static void init(DaoFactory daoFactory) throws ServletException, DaoException {
        profileDao = daoFactory.getProfileDao();
    }

    static void getNews(HttpServletRequest request,
                        HttpServletResponse response)
            throws ServletException, IOException {
        PageBuilder.getDefault(request,response);
    }

    static void getWall(HttpServletRequest request,
                        HttpServletResponse response)
            throws ServletException, IOException {

    }

    static void postNews(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

    }

    static void postWall(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

    }
}
