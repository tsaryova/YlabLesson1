package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;

import javax.sql.DataSource;
import java.sql.SQLException;

public class ApiApp {

    private final static String EXCHANGE_NAME = "myExchange";
    private final static String QUEUE_NAME = "queue";
    private final static String ROUTING_KEY = "testRoute";

    public static void main(String[] args) throws Exception {
        DataSource dataSource = initDb();
        ConnectionFactory connectionFactory = initMQ();

        // Тут пишем создание PersonApi, запуск и демонстрацию работы
        PersonApi personApi = new PersonApiImpl(dataSource, connectionFactory);

        for (long i = 1; i <= 15; i++) {
            personApi.savePerson(i, "Name" + i, "LastName" + i, "MiddleName" + 1);
        }

        personApi.deletePerson(2L);
        personApi.deletePerson(2L); // в DbApp выведется сообщение о попытке удаления
        System.out.println(personApi.findPerson(1L) == null); //false
        System.out.println(personApi.findPerson(2L) == null); //true

        personApi.savePerson(3L, "UpdateName", "Last333", "333Middle");

        personApi.findAll().forEach(person -> System.out.println(person.getName())); //Name0-Name15 без Name2 (вероятно запрос на измение person_id = 3 не успел ещё обработаться)
        // Если не успел обработаться запрос на измененение person id = 3
        Thread.sleep(5000);
        System.out.println("=======================");
        personApi.findAll().forEach(person -> System.out.println(person.getName())); //Name0-Name15 без Name2. UpdateName у person_id = 3

    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }

    private static DataSource initDb() throws SQLException {
        DataSource dataSource = DbUtil.buildDataSource();
        return dataSource;
    }

}
