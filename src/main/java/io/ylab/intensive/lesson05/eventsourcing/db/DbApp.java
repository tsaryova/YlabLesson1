package io.ylab.intensive.lesson05.eventsourcing.db;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DbApp {

    private final static String QUEUE_NAME = "queue";

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        // тут пишем создание и запуск приложения работы с БД

        ConnectionFactory connectionFactory = applicationContext.getBean(ConnectionFactory.class);
        MessagePersonOperation messagePersonOperation = applicationContext.getBean(MessagePersonOperation.class);

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            ObjectMapper mapper = new ObjectMapper();

            while (!Thread.currentThread().isInterrupted()) {
                GetResponse message = channel.basicGet(QUEUE_NAME, true);
                if (message != null) {
                    String received = new String(message.getBody());

                    JsonNode rootNode = mapper.readTree(received);
                    String operation = rootNode.get("operation").textValue();

                    messagePersonOperation.doDbOperation(operation, rootNode);
                }
            }
        }

        applicationContext.stop();
    }
}
