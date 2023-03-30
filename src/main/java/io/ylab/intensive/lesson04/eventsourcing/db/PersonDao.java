package io.ylab.intensive.lesson04.eventsourcing.db;

import io.ylab.intensive.lesson04.eventsourcing.Person;

public interface PersonDao {

    void deletePerson(Long personId);

    void savePerson(Person person);

    Person findPerson(Long personId);

}
