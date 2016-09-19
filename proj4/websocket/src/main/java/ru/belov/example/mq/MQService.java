package ru.belov.example.mq;

/**
 * Created by john on 8/26/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class MQService {

    void myFooTry(){

        Sender sender = new Sender();
        sender.sendMessage();

        Receiver receiver = new Receiver();
        receiver.receiveMessage();

    }
}
