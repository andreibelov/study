package ru.belov.example.mq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Created by john on 9/7/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class Sender {

    private ConnectionFactory factory = null;
    private Connection connection = null;
    private Session session = null;
    private Destination myQueue = null;
    private MessageProducer producer = null;

    public Sender() {

    }

    public void sendMessage() {

        try {
            factory = new ActiveMQConnectionFactory(
                    ActiveMQConnection.DEFAULT_BROKER_URL);
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            myQueue = session.createQueue("SAMPLEQUEUE");
            producer = session.createProducer(myQueue);
            TextMessage message = session.createTextMessage();
            message.setText("Hello ...This is a sample message..sending from FirstClient");
            producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
