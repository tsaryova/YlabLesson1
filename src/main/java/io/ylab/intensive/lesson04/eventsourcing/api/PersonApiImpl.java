package io.ylab.intensive.lesson04.eventsourcing.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import io.ylab.intensive.lesson04.eventsourcing.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

/**
 * Тут пишем реализацию
 */

public class PersonApiImpl implements PersonApi {
    private DataSource dataSource;
    private static Logger logger = LoggerFactory.getLogger(PersonApiImpl.class);

    public PersonApiImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void deletePerson(Long personId) {
        if (findPerson(personId) != null) {
            String removeSql = "DELETE FROM person WHERE person_id=?";
            try (Connection connection = this.dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(removeSql)) {
                setDataPrepareStatement(preparedStatement, 1, personId);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            logger.info("Была выполнена попытка удаления персоны с id = " + personId + ". Персоны с таким id не существует.");
        }
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        String sqlQuery = "";
        if (findPerson(personId) == null) {
            sqlQuery = "INSERT INTO person (first_name, last_name, middle_name, person_id) VALUES(?, ?, ?, ?)";
        } else {
            sqlQuery = "UPDATE person SET first_name=?, last_name=?, middle_name=?  WHERE person_id=?";
        }
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, middleName);
            setDataPrepareStatement(preparedStatement, 4, personId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
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
}
