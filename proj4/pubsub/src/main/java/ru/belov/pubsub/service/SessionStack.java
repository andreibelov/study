package ru.belov.pubsub.service;

import com.google.gson.Gson;
import ru.belov.pubsub.model.ChatMessage;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by john on 9/19/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class SessionStack {

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
            try {
                session.getBasicRemote().sendText(gson.toJson(message, ChatMessage.class));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
