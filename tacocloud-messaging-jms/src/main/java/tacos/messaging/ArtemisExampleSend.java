package tacos.messaging;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;

public class ArtemisExampleSend {
    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String USERNAME = "artemis";
    private static final String PASSWORD = "artemis";

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL, USERNAME, PASSWORD);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("queue.example");

        MessageProducer messageProducer = session.createProducer(destination);
        TextMessage textMessage = session.createTextMessage();
        textMessage.setText("Hello, Artemis!");

        messageProducer.send(textMessage);
        System.out.println("Message sent successfully!");

        connection.close();
    }
}