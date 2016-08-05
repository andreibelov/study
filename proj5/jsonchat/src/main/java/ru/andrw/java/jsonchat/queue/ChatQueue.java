package ru.andrw.java.jsonchat.queue;

import org.jetbrains.annotations.Contract;

import java.util.Queue;

/**
 * Created by user on 7/4/2016.
 */
public class ChatQueue {
    private Queue<ChatRequest> queue;


    @Contract(pure = true)
    private boolean myMethod(){
        try {
            throw null;
        } finally {

            if(true) return true;
        }
    }

}
