package br.com.caelum.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import java.util.Scanner;

public class TesteProdutorTopico {

    public static void main(String[] args) throws Exception {
        InitialContext context = new InitialContext();
        ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = connectionFactory.createConnection("user", "senha");
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination topico = (Destination) context.lookup("loja");

        MessageProducer producer = session.createProducer(topico);
        Message message = session.createTextMessage("<pedido>"
                + "<id>222</id>"
                + "</pedido>");
//        message.setBooleanProperty("ebook", true);
        producer.send(message);


        new Scanner(System.in).next();

        producer.close();
        session.close();
        connection.close();
        context.close();
    }
}
