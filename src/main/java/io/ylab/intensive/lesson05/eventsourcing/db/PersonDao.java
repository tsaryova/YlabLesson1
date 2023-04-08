package io.ylab.intensive.lesson05.eventsourcing.db;

import io.ylab.intensive.lesson05.eventsourcing.Person;

public interface PersonDao {

    void deletePerson(Long personId);

    void savePerson(Person person);

    Person findPerson(Long personId);

}
