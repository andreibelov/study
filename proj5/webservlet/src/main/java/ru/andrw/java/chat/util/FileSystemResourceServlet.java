package ru.andrw.java.chat.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by john on 10/5/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet("/files/*")
public class FileSystemResourceServlet extends StaticResourceServlet {

    private File folder;

    @Override
    public void init() throws ServletException {
        folder = new File("/path/to/the/folder");
    }

    @Override
    protected StaticResource getStaticResource(HttpServletRequest request) throws IllegalArgumentException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.isEmpty() || "/".equals(pathInfo)) {
            throw new IllegalArgumentException();
        }

        String name = null;
        try {
            name = URLDecoder.decode(pathInfo.substring(1), StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final File file = new File(folder, name);

        return !file.exists() ? null : new StaticResource() {
            @Override
            public long getLastModified() {
                return file.lastModified();
            }
            @Override
            public InputStream getInputStream() throws IOException {
                return new FileInputStream(file);
            }
            @Override
            public String getFileName() {
                return file.getName();
            }
            @Override
            public long getContentLength() {
                return file.length();
            }
        };
    }

}