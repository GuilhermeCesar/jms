package br.com.caelum.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Scanner;

public class TesteConsumidorDLQ {

    public static void main(String[] args) throws Exception {
        InitialContext context = new InitialContext();
        ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination fila = (Destination) context.lookup("DLQ");
        MessageConsumer consumer = session.createConsumer(fila);

        consumer.setMessageListener(message -> {
            System.out.println(message);
        });

        new Scanner(System.in).next();

        session.close();
        connection.close();
        context.close();
    }
}
