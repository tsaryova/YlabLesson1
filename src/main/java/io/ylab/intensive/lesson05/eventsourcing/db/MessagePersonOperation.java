package io.ylab.intensive.lesson05.eventsourcing.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessagePersonOperation {

    private PersonDao personDao;

    @Autowired
    public MessagePersonOperation(PersonDao personDao) {
        this.personDao = personDao;
    }

    public void doDbOperation(String operation, JsonNode rootNode) {
        switch (operation) {
            case "save":
                savePerson(rootNode);
                break;
            case "delete":
                deletePerson(rootNode);
                break;
            default:
                System.err.println("This operation is absent");
                break;
        }
    }

    private void savePerson(JsonNode rootNode) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode personDataJson = rootNode.get("person");
        Person person = null;

        try {
            person = mapper.readValue(personDataJson.toString(), Person.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        this.personDao.savePerson(person);
    }

    private void deletePerson(JsonNode rootNode) {
        Long personId = rootNode.get("person_id").longValue();
        if (personId == null || personId <= 0) {
            System.err.println("Uncorrect person_id");
        } else {
            this.personDao.deletePerson(personId);
        }
    }


}
