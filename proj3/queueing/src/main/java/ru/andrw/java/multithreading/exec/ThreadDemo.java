package ru.andrw.java.multithreading.exec;

import ru.andrw.java.multithreading.queue.Queueing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by john on 7/6/2016.
 */
public class ThreadDemo {

    public void doWork(){
        //limit the number of actual threads
        int poolSize = 10;
        ExecutorService service = Executors.newFixedThreadPool(poolSize);
        ThreadGroup mainGroup = new ThreadGroup("mainGroup");
        List<Future> futures =
        IntStream.rangeClosed(1, 25)
                .mapToObj(MyThread::new)
                .map(service::submit)
                .collect(Collectors.toList());

        futures.forEach(f -> {
            try {
                ((Future<Runnable>) f).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        Queueing.printAll();
    }

}
class MyThread implements Runnable {

    private final int id;

    MyThread(int id){
        this.id = id;
    }

    @Override
    public void run() {
        Queueing.putMessage("Hello from a thread",id);
    }
}
class MyAutostartThread extends Thread {
    public MyAutostartThread(ThreadGroup group, String name) {
        // We set the name here instead.
        super(group, "mythread");
        setName(name);
        System.out.println(Thread.currentThread()+" will start immediately!");
        start(); //?????
    }
}