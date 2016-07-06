package ru.andrw.java.multithreading.workers;

import ru.andrw.java.multithreading.queue.Queueing;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by john on 7/6/2016.
 */
public class Manager {

    private final Object lock = new Object();
    private static BlockingQueue<Thread> queue = new ArrayBlockingQueue<>(20);
    private ThreadGroup mainGroup = new ThreadGroup("mainGroup");


    public void doWork() {

        System.out.println(Thread.currentThread());

        Thread thread; int i = 0;
        Queueing.setLock(lock);
        do thread = (new Thread(mainGroup,new Worker(i++)));
        while (queue.offer(thread));
        queue.forEach(Thread::start);
        queue.stream().forEach(e -> {
            try {
                e.join();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });

        Queueing.printAll();
    }

}
class Worker implements Runnable {
    private final int id;
    Worker(int i){
        this.id = i;
    }

    @Override
    public void run() { Queueing.putMessage("Hello from a thread",id); }
    public int getId(){return this.id;}
}
