package br.com.caelum.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Scanner;

import static javax.jms.Session.SESSION_TRANSACTED;

public class TesteConsumidorFila {

    public static void main(String[] args) throws Exception {
        InitialContext context = new InitialContext();
        ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = connectionFactory.createConnection("user", "senha");
        connection.start();

        Session session = connection.createSession(true, SESSION_TRANSACTED);

        Destination fila = (Destination) context.lookup("financeiro");
        MessageConsumer consumer = session.createConsumer(fila);

        consumer.setMessageListener(message -> {
            TextMessage textMessage = (TextMessage) message;

            try {
//                message.acknowledge();
                System.out.println(textMessage.getText());
                session.commit();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });

        new Scanner(System.in).next();

        session.close();
        connection.close();
        context.close();
    }
}
