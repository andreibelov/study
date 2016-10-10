package com.example.chat.dao.impl;

import com.example.chat.dao.MessageDao;
import com.example.chat.dao.impl.postgres.PgMessageDao;
import com.example.chat.model.Message;
import com.example.chat.util.DbUtil;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.Date;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * com.example.chat.dao.impl.postgres.PgMessageDao Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre></pre>
 */
public class PgMessageDaoTest {

    private Supplier<Connection> connectionSupplier;
    private MessageDao messageDao;

    @Before
    public void setUp() throws Exception {
        connectionSupplier = new DbUtil();
        messageDao = new PgMessageDao(connectionSupplier);
    }

    /**
     * Method: addMessage
     */
    @Test
    public void addMessage() throws Exception {

        Message message = new Message()
                .setText("simple text goes here")
                .setTimestamp(new Date())
                .setUuid(UUID.randomUUID());
        System.out.println(messageDao
                .addMessage(message));
    }
    /**
     * Method: getMessageByUuid
     */
    @Test
    public void getMessageByUuid() throws Exception {

        Message message = messageDao
                .getMessageByUuid("982623e4-f383-400b-9dbb-d91fa46ba03c");

        System.out.println(message);

    }


    /**
     * Method: getMessagesByUser
     */
    @Test
    public void getMessagesByUser() throws Exception {
        for (Message msg :
                messageDao.getMessagesByUser(0)) {
            System.out.println(msg);
        }
    }

    /**
     * Method: getMessagesByPageNum
     */
    @Test
    public void getMessagesByPageNum() throws Exception {
        for (Message msg :
                messageDao.getMessagesByPageNum(0,0)) {
            System.out.println(msg);
        }
    }

}