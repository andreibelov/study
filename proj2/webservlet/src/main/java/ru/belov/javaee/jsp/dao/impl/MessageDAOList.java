package ru.belov.javaee.jsp.dao.impl;

import lombok.Data;
import ru.belov.javaee.jsp.dao.DAOException;
import ru.belov.javaee.jsp.dao.MessageDAO;
import ru.belov.javaee.jsp.model.ChatUser;
import ru.belov.javaee.jsp.model.Message;
import ru.belov.javaee.jsp.model.User;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by john on 7/22/2016.
 */
public class MessageDAOList implements MessageDAO{

    private static final MessageDAOList INSTANCE;
    private static final List<Message> messages;

    private MessageDAOList(){}

    static {
        String path = "db.xml";
        Messages msgs;
//        try(InputStream inputStream = Messages.class.getResourceAsStream(path))
//        {
//            msgs = (Messages) JAXBContext.newInstance(Messages.class)
//                    .createUnmarshaller().unmarshal(inputStream);
//
//        } catch (JAXBException | IOException e) {
//
//        }
        msgs = new Messages();
        messages = msgs.getMessageList();
        INSTANCE = new  MessageDAOList();
//        JAXBContext jaxbContext;
//        try{
//            jaxbContext = JAXBContext.newInstance(Messages.class);
//            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            File file = new File(path);
//            jaxbMarshaller.marshal(msgs, file);
//        }catch (JAXBException e){System.out.println(e.toString());}
    }

    public static MessageDAOList getInstance(){
        return INSTANCE;
    }

    @Override
    public Message create(String text, ChatUser chatUser) throws IllegalArgumentException, DAOException {
        Message message = new Message().setFrom(chatUser)
                .setTimestamp(LocalDateTime.now())
                .setText(text)
                .setUuid(UUID.randomUUID());
        messages.add(message);
        return message;

    }

    @Override
    public Optional<Message> find(Long id) throws DAOException {
        return null;
    }

    @Override
    public Optional<Message> find(String text) throws DAOException {
        return null;
    }

    @Override
    public Optional<Message> find(ChatUser from) throws DAOException {
        return null;
    }

    @Override
    public List<Message> list(Long indx, int amount) throws DAOException {
        return null;
    }
}
