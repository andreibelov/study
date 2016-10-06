package ru.andrw.java.chat.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import ru.andrw.java.chat.model.FileEntity;
import ru.andrw.java.chat.service.EntityService;

/**
 * Created by john on 10/5/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet("/dbfiles/*")
public class EntityResourceServlet extends StaticResourceServlet {

//    @EJB
    private EntityService yourEntityService;

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
        final FileEntity yourEntity = yourEntityService.getByName(name);

        return (yourEntity == null) ? null : new StaticResource() {
            @Override
            public long getLastModified() {
                return yourEntity.getLastModified();
            }
            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(yourEntityService.getContentById(yourEntity.getId()));
            }
            @Override
            public String getFileName() {
                return yourEntity.getName();
            }
            @Override
            public long getContentLength() {
                return yourEntity.getContentLength();
            }
        };
    }

}