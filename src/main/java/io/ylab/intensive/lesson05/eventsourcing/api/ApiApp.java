package io.ylab.intensive.lesson05.eventsourcing.api;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApiApp {
    public static void main(String[] args) throws Exception {
        // Тут пишем создание PersonApi, запуск и демонстрацию работы
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        PersonApi personApi = applicationContext.getBean(PersonApi.class);
        // пишем взаимодействие с PersonApi
        workWithPersonApi(personApi);

        applicationContext.stop();
    }

    private static void workWithPersonApi(PersonApi personApi) throws Exception {
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
}
