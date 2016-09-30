package ru.andrw.java.socialnw.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.diffplug.common.base.Errors;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.andrw.java.socialnw.service.UploadService;

/**
 * Created by john on 9/26/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet(name="mytest",
        urlPatterns={"/upload"},
        initParams = {
                @WebInitParam(name="file-upload", value="D:\\Users\\tomcat9\\Downloads\\")})
public class Upload extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger("ru.andrw.java.socialnw.servlet.Upload");

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext sc = config.getServletContext();
        // Get the file location where it would be stored.
        UploadService.setFolder(getInitParameter("file-upload"));

    }

    public void doGet(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("pageTitle", "File Upload");
        request.setAttribute("includeSection", "/WEB-INF/include/upload.jsp");
        request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").include(request, response);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {

        UploadService.performUpload(request,response);
    }
}
