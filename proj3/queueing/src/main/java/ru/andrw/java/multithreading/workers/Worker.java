package ru.andrw.java.multithreading.workers;

import ru.andrw.java.multithreading.queue.Queueing;

/**
 * Created by john on 7/5/2016.
 */
public class Worker implements Runnable {
    private final int id;
    Worker(int i){
        this.id = i;
    }

    @Override
    public void run() { Queueing.putMessage("Hello from a thread",id); }
    public int getId(){return this.id;}
}
