package ru.andrw.java.socialnw.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.andrw.java.socialnw.model.Section;
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
        // Get the file location where it would be stored.
        UploadService.setFolder(getInitParameter("file-upload"));

    }

    public void doGet(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        Section section = (new Section())
                .setPageTitle("File Upload")
                .setJspFile("upload.jsp");
        request.setAttribute("section",section);
        request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").include(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {

        UploadService.performUpload(request,response);
    }
}
