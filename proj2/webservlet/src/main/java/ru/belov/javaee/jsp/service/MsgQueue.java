package ru.belov.javaee.jsp.service;

import ru.belov.javaee.jsp.model.Message;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by john on 7/22/2016.
 */
public class MsgQueue implements Serializable {
    private final List<Deque<Message>> register = new CopyOnWriteArrayList<>();
    private final Deque<Message> messages = new ConcurrentLinkedDeque<>();

    void registerNewDeque(Deque<Message> deque){
        register.add(deque);
    }

    public void addNewMsg(Message message){
        for (Deque<Message> deq :register) {
            deq.push(message);
        }
    }

}
