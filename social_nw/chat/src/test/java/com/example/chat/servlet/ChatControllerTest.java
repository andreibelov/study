package com.example.chat.servlet;

import com.example.chat.dao.MessageDao;
import com.example.chat.dao.impl.postgres.PgMessageDao;
import com.example.chat.model.JsonPack;
import com.example.chat.model.Message;
import com.example.chat.model.User;
import com.example.chat.util.DbUtil;
import com.google.gson.Gson;
import org.apache.commons.io.output.StringBuilderWriter;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;
import java.io.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.Date;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * com.example.chat.servlet.ChatController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre></pre>
 */
public class ChatControllerTest {


    private Supplier<Connection> connectionSupplier;
    private MessageDao messageDao;
    private Gson gson = new Gson();

    @Before
    public void setUp() throws Exception {
        connectionSupplier = new DbUtil();
        messageDao = new PgMessageDao(connectionSupplier);
    }

    /**
     * Method: doPost
     */
    @Test
    public void doPost() throws Exception {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringBuilderWriter sw = new StringBuilderWriter();
        PrintWriter writer = new PrintWriter(new BufferedWriter(sw));
        User user = new User().setUserid(0);

        when(request.getParameter("text")).thenReturn("aleih alsjkcae% (*&#@)#*(@)");
        when(request.getParameter("chat_room_is")).thenReturn("0");
        when(response.getWriter()).thenReturn(writer);



        Message message = new Message()
                .setText(request.getParameter("text"))
                .setTimestamp(new Date())
                .setChatRoomId(Integer.parseInt(request.getParameter("chat_room_is")))
                .setFromUserId(user.getUserid())
                .setUuid(UUID.randomUUID());
        JsonPack pack = new JsonPack()
                .setMessage(messageDao.addMessage(message));
        response.getWriter().print(gson.toJson(pack, JsonPack.class));
        sw.write(gson.toJson(pack, JsonPack.class));
        sw.flush();
        System.out.println(sw.getBuilder());
    }

}