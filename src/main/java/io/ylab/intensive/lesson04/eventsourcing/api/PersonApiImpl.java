package io.ylab.intensive.lesson04.eventsourcing.api;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;

/**
 * Тут пишем реализацию
 */

public class PersonApiImpl implements PersonApi {
    private DataSource dataSource;
    private ConnectionFactory connectionFactory;

    private final static String EXCHANGE_NAME = "myExchange";
    private final static String QUEUE_NAME = "queue";
    private final static String ROUTING_KEY = "testRoute";

    public PersonApiImpl(DataSource dataSource, ConnectionFactory connectionFactory) {
        this.dataSource = dataSource;
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void deletePerson(Long personId) {
        String message = "{\"operation\":\"delete\", \"person_id\": " + personId + "}";
        sendMessage(message);
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        Person person = new Person(personId, firstName, lastName, middleName);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String message = "{\"operation\":\"save\", \"person\": " + objectMapper.writeValueAsString(person) + "}";
            sendMessage(message);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Person findPerson(Long personId) {
        String findPersonSQL = "SELECT * FROM person WHERE person_id=?";
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findPersonSQL)) {
            setDataPrepareStatement(preparedStatement, 1, personId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getLong("person_id"));
                person.setName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setMiddleName(resultSet.getString("middle_name"));

                return person;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Person> findAll() {
        List<Person> people = new ArrayList<>();

        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM person")) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getLong("person_id"));
                person.setName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setMiddleName(resultSet.getString("middle_name"));

                people.add(person);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return people;
    }

    private void setDataPrepareStatement(PreparedStatement preparedStatement, int index, Long value) {
        try {
            if (value == null) {
                preparedStatement.setNull(index, Types.BIGINT);
            } else {
                preparedStatement.setLong(index, value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        try (com.rabbitmq.client.Connection connection = this.connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
