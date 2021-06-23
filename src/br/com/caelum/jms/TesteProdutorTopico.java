package br.com.caelum.jms;

import br.com.caelum.modelo.Pedido;
import br.com.caelum.modelo.PedidoFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.xml.bind.JAXB;
import java.io.StringWriter;
import java.util.Scanner;

public class TesteProdutorTopico {

    public static void main(String[] args) throws Exception {
        InitialContext context = new InitialContext();
        ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination topico = (Destination) context.lookup("loja");

        MessageProducer producer = session.createProducer(topico);

        Pedido pedido = new PedidoFactory().geraPedidoComValores();

//        StringWriter writer = new StringWriter();
//        JAXB.marshal(pedido, writer);
//        String xml = writer.toString();
//        System.out.println(xml);

        Message message = session.createObjectMessage(pedido);
//        message.setBooleanProperty("ebook", true);
        producer.send(message);


        new Scanner(System.in).next();

        producer.close();
        session.close();
        connection.close();
        context.close();
    }
}
