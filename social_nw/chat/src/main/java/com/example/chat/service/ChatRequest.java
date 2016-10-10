package com.example.chat.service;

import com.google.gson.Gson;

import com.example.chat.model.chat.ChatMessage;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.websocket.Session;

/**
 * Created by user on 7/4/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class ChatRequest {
    private static List<Session> mainStack = new CopyOnWriteArrayList<>();
    private static Gson gson = new Gson();

    public static void addNewSession(Session session) {
        mainStack.add(session);
    }

    public static void removeSession(Session session) {
        mainStack.remove(session);
    }

    public static void sendToAll(ChatMessage message){
        for (Session session : mainStack) {
                session.getAsyncRemote().sendText(gson.toJson(message, ChatMessage.class));
        }
    }
}
