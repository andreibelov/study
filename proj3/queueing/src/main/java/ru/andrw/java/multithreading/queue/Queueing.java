package ru.andrw.java.multithreading.queue;

import ru.andrw.java.multithreading.data.Message;
import ru.andrw.java.multithreading.workers.Worker;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by john on 7/5/2016.
 */
public class Queueing {

    private static Object lock;

    private final BlockingDeque<Message> messages = new LinkedBlockingDeque<>();
    private final Deque<Message> deque = new ConcurrentLinkedDeque<>();
    private static final List<Message> messageList = new LinkedList<>();

    public static void putMessage(String text, int fromId){
        messageList.add((new Message()).setText(text).setFrom(fromId));
    }

    public static void printAll() {messageList.forEach(System.out::println);}

    public static void setLock(Object lock) {
        Queueing.lock = lock;
    }
}
