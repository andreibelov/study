package ru.andrw.java.socialnw.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by john on 9/26/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@FunctionalInterface
interface ServiceMethod {
    void execute(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException;
}
