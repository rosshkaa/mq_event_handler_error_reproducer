package org.example;

import com.sun.messaging.ConnectionFactory;
import com.sun.messaging.Destination;
import com.sun.messaging.jms.Connection;
import com.sun.messaging.jms.notification.Event;
import com.sun.messaging.jms.notification.EventListener;
import jakarta.jms.JMSException;
import jakarta.jms.Topic;
import jakarta.jms.TopicConnection;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);
    private static final long limit = 1000000;

    public Main() {
    }

    public static void main(String[] args) {
        try {
            TopicConnection connection = new ConnectionFactory().createTopicConnection();
            Topic topic = new com.sun.messaging.Topic("test_topic");

            Logger.getLogger(Main.class.getName()).log(Level.INFO, "Submitting %d tasks".formatted(limit));
            for (long i = 0; i < limit; i++) {
                executor.submit(new ConsumerEventsEmulator((Connection) connection, (Destination) topic));
            }
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.INFO, e.getMessage(), e);
        }

        exit();
    }

    private static void exit() {
        Logger.getLogger(Main.class.getName()).log(Level.INFO, "Awaiting executor termination");
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException ignore) {
        }

        System.exit(0);
    }

    record ConsumerEventsEmulator(Connection connection, Destination destination) implements Runnable {
        @Override
        public void run() {
            try {
                connection.stop();
                connection.setConsumerEventListener(destination, new ConsumerListener());
                connection.start();
                connection.removeConsumerEventListener(destination);
            } catch (JMSException ignore) {
            }
        }
    }

    static class ConsumerListener implements EventListener {
        @Override
        public void onEvent(Event event) {
//            Logger.getLogger(Main.class.getName()).log(Level.INFO, null, event.getEventMessage());
        }
    }
}
