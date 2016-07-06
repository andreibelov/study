package ru.andrw.java.multithreading.workers;

import ru.andrw.java.multithreading.queue.Queueing;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by john on 7/6/2016.
 */
public class Manager {

    private final Object lock = new Object();
    private static BlockingQueue<Thread> queue = new ArrayBlockingQueue<>(20);

    public void doWork() {
        Thread thread; int i = 0;
        Queueing.setLock(lock);
        do thread = new Thread(new Worker(i++));
        while (queue.offer(thread));
        queue.forEach(Thread::start);
        synchronized (lock) {Queueing.printAll();}
    }
}
