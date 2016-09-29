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
    private String projectName;

    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 50 * 1024 * 1024;
    private int maxMemSize = 400 * 1024;
    private File file;
    private UUID uuid = new UUID(4242L, 4242L);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext sc = config.getServletContext();
        projectName = (String) sc.getAttribute("projectName");
        // Get the file location where it would be stored.
        filePath = getInitParameter("file-upload");
    }

    public void doGet(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("pageTitle", "File Upload");
        request.setAttribute("PROJECT_NAME", "Vault 101");
        request.setAttribute("includeSection", "/WEB-INF/include/upload.jsp");
        request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").include(request, response);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {

        String message = "";

        if( ServletFileUpload.isMultipartContent(request) ) {

            DiskFileItemFactory factory = new DiskFileItemFactory(maxMemSize,new File("c:\\temp"));

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(maxFileSize);

            try {
                // Parse the request to get file items.
                List<FileItem> fileItems = upload.parseRequest(request);
                logger.info("Size of List<FileItem>" + fileItems.size());
                // Process the uploaded file items
                fileItems.forEach(Errors.rethrow().wrap(fi -> {
                    if (!fi.isFormField()) {

                        String fieldName = fi.getFieldName();
                        String fileName = fi.getName();
                        fileName = UUID.nameUUIDFromBytes(fileName.getBytes()).toString() +
                                fileName.substring(fileName.lastIndexOf("."));
                        String contentType = fi.getContentType();
                        boolean isInMemory = fi.isInMemory();
                        long sizeInBytes = fi.getSize();
                            logger.info(filePath);
                        file = new File(filePath+fileName);
                        logger.info(file.toString());
                        fi.write(file);
                    }
                }));

                message = "File uploaded to " + file;

            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                request.setAttribute("errorMessage",ex.getMessage());
                request.setAttribute("pageTitle", "File Upload");
                request.setAttribute("PROJECT_NAME", "Vault 101");
                request.setAttribute("includeSection", "/WEB-INF/include/upload.jsp");
                request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").include(request, response);
            }
        } else {
            message = "You've sent wrong request. Probably it's not a file";
        }
        request.setAttribute("errorMessage",message);
        request.setAttribute("pageTitle", "File Upload");
        request.setAttribute("PROJECT_NAME", "Vault 101");
        request.setAttribute("includeSection", "/WEB-INF/include/upload.jsp");
        request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").include(request, response);
    }
}
