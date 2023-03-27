package io.ylab.intensive.lesson04.eventsourcing.db;

import io.ylab.intensive.lesson04.eventsourcing.Person;
import io.ylab.intensive.lesson04.eventsourcing.api.PersonApiImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class PersonDaoImpl implements PersonDao{

    private DataSource dataSource;
    private static Logger logger = LoggerFactory.getLogger(PersonApiImpl.class);

    public PersonDaoImpl(DataSource dataSource) {
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
    public void savePerson(Person person) {
        String sqlQuery = "";
        if (findPerson(person.getId()) == null) {
            sqlQuery = "INSERT INTO person (first_name, last_name, middle_name, person_id) VALUES(?, ?, ?, ?)";
        } else {
            sqlQuery = "UPDATE person SET first_name=?, last_name=?, middle_name=?  WHERE person_id=?";
        }
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setString(3, person.getMiddleName());
            setDataPrepareStatement(preparedStatement, 4, person.getId());

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
