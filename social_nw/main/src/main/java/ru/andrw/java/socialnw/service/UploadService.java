package ru.andrw.java.socialnw.service;

import com.diffplug.common.base.Errors;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.andrw.java.socialnw.model.view.Section;

/**
 * Created by john on 9/27/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class UploadService {

    private static final Logger logger = LoggerFactory
            .getLogger("ru.andrw.java.socialnw.service.UploadService");

    private static String uploadFolder;
    private static boolean isMultipart;
    private static int maxFileSize = 50 * 1024 * 1024;
    private static int maxMemSize = 400 * 1024;
    private UUID uuid = new UUID(4242L, 4242L);

    public static void performUpload(HttpServletRequest request,
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
                // Process the uploaded file items
                final String[] filePath = new String[1];
                fileItems.forEach(Errors.rethrow().wrap(fi -> {
                    if (!fi.isFormField()) {
                        String fieldName = fi.getFieldName();
                        String fileName = fi.getName();
                        fileName = UUID.nameUUIDFromBytes(fileName.getBytes()).toString() +
                                fileName.substring(fileName.lastIndexOf("."));
                        String contentType = fi.getContentType();
                        boolean isInMemory = fi.isInMemory();
                        long sizeInBytes = fi.getSize();
                        fi.write(new File(uploadFolder+fileName));
                        filePath[0] = fileName;
                        logger.info("File uploaded to "+uploadFolder+fileName);
                    }
                }));
                message = "File uploaded to " + filePath[0];

            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                request.setAttribute("errorMessage",ex.getMessage());
                Section section = (new Section())
                        .setPageTitle("File Upload")
                        .setJspFile("upload.jsp");
                request.setAttribute("section",section);
                request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").include(request, response);
            }
        } else {
            message = "You've sent wrong request. Probably it's not a file";
        }
        request.setAttribute("errorMessage",message);
        Section section = (new Section())
                .setPageTitle("File Upload")
                .setJspFile("upload.jsp");
        request.setAttribute("section",section);
        request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").include(request, response);
    }

    public static void setFolder(String folder) {
        UploadService.uploadFolder = folder;
    }
}
