package io.ylab.intensive.lesson04.eventsourcing.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;

public class DbApp {

    private final static String QUEUE_NAME = "queue";

    public static void main(String[] args) throws Exception {
        DataSource dataSource = initDb();
        ConnectionFactory connectionFactory = initMQ();
        // тут пишем создание и запуск приложения работы с БД

        PersonDao personDao = new PersonDaoImpl(dataSource);

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            while (!Thread.currentThread().isInterrupted()) {
                GetResponse message = channel.basicGet(QUEUE_NAME, true);
                if (message != null) {
                    String received = new String(message.getBody());

                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode rootNode = mapper.readTree(received);
                    String operation = rootNode.get("operation").textValue();

                     switch (operation) {
                         case "save":
                             JsonNode personDataJson = rootNode.get("person");
                             Person person = mapper.readValue(personDataJson.toString(), Person.class);
                             personDao.savePerson(person);
                             break;
                         case "delete":
                             Long personId = rootNode.get("person_id").longValue();
                             if (personId == null || personId <= 0) {
                                 System.err.println("Uncorrect person_id");
                             } else {
                                 personDao.deletePerson(personId);
                             }
                             break;
                         default:
                             System.err.println("This operation is absent");
                             break;
                     }

                }
            }
        }
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }

    private static DataSource initDb() throws SQLException {
        String ddl = ""
                + "drop table if exists person;"
                + "create table if not exists person (\n"
                + "person_id bigint primary key,\n"
                + "first_name varchar,\n"
                + "last_name varchar,\n"
                + "middle_name varchar\n"
                + ")";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(ddl, dataSource);
        return dataSource;
    }
}
