package ru.andrw.java.jsonchat.service;

import com.google.gson.Gson;
import ru.andrw.java.jsonchat.model.chat.ChatMessage;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Created by john on 9/20/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@ServerEndpoint("/chatty")
public class ChatEndpoint {
    private Gson gson = new Gson();

    /**
     * @OnOpen allows us to intercept the creation of a new session.
     * The session class allows us to send data to the user.
     * In the method onOpen, we'll let the user know that the handshake was
     * successful.
     */
    @OnOpen
    public void onOpen(Session session){

        try {
            RemoteEndpoint.Basic client = session.getBasicRemote();
            ChatMessage message = (new ChatMessage())
                    .setSender("server")
                    .setText("Connection success. Session ID - "+session.getId());
            client.sendText(gson.toJson(message, ChatMessage.class));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        ChatRequest.sendToAll((new ChatMessage())
                .setSender("server")
                .setText("user" + session.getId() + " joined conversation"));
        ChatRequest.addNewSession(session);
    }

    /**
     * When a user sends a message to the server, this method will intercept the message
     * and allow us to react to it. For now the message is read as a String.
     */
    @OnMessage
    public void onMessage(String message, Session session){

        ChatRequest.sendToAll((new ChatMessage())
                .setSender("User "+session.getId())
                .setText(message));

    }

    /**
     * The user closes the connection.
     *
     * Note: you can't send messages to the client from this method
     */
    @OnClose
    public void onClose(Session session){
        ChatRequest.removeSession(session);
        ChatRequest.sendToAll((new ChatMessage())
                .setSender("server")
                .setText("user" +session.getId()+" left conversation"));
    }
}
