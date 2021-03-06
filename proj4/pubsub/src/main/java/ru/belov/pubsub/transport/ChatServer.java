package ru.belov.pubsub.transport;

import com.google.gson.Gson;
import ru.belov.pubsub.model.ChatMessage;
import ru.belov.pubsub.service.SessionStack;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @ServerEndpoint gives the relative name for the end point
 * This will be accessed via ws://localhost:8080/websocket/echo
 * Where "localhost" is the address of the host,
 * "EchoChamber" is the name of the package
 * and "echo" is the address to access this class from the server
 */
@ServerEndpoint("/chat")
public class ChatServer {

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

        SessionStack.sendToAll((new ChatMessage())
                .setSender("server")
                .setText("user " + session.getId() + " joined the chat"));
        SessionStack.addNewSession(session);
    }

    /**
     * When a user sends a message to the server, this method will intercept the message
     * and allow us to react to it. For now the message is read as a String.
     */
    @OnMessage
    public void onMessage(String message, Session session){

        SessionStack.sendToAll((new ChatMessage())
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
        SessionStack.removeSession(session);
        SessionStack.sendToAll((new ChatMessage())
                .setSender("server")
                .setText("Session " +session.getId()+" has ended"));
    }


}