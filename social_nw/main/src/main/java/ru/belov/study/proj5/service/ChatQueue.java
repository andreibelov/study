package ru.belov.study.proj5.service;

import ru.belov.study.proj5.model.chat.ChatMessage;

import java.io.Serializable;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by john on 7/22/2016.
 */
public class ChatQueue implements Serializable {
    private final List<Deque<ChatMessage>> register = new CopyOnWriteArrayList<>();
    private final Deque<ChatMessage> messages = new ConcurrentLinkedDeque<>();

    public void registerNewDeque(Deque<ChatMessage> deque){
        register.add(deque);
    }

    public void addNewMsg(ChatMessage message){
        for (Deque<ChatMessage> deq :register) {
            deq.push(message);
        }
    }

}
