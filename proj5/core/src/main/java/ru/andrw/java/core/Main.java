package ru.andrw.java.core;

import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * Created by john on 7/1/2016.
 *
 */

public class Main {
    public static void main(String[] args) {for (int i = 0; i <100; i++) (new Thread(new MyClass(i))).start();}
}

class MyClass implements Runnable{

    private final int i;

    MyClass(int i) {this.i = i;}

    @Override
    public void run() {
        System.out.println("Thread "+i+" has started.");
        Random rnd = new Random();
        try {
            sleep(rnd.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread "+i+" has finished job.");
    }
}