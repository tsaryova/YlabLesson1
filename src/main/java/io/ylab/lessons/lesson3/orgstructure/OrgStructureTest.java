package io.ylab.lessons.lesson3.orgstructure;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class OrgStructureTest {
    public static void main(String[] args) throws IOException {
        File csvFile = new File("src/main/resources/lessons/lesson3/orgstructure/structure.csv");
        OrgStructureParser parser = new OrgStructureParserImpl();
        Employee boss = parser.parseStructure(csvFile);
        System.out.println(boss.getId());
        List<Employee> subordinates = boss.getSubordinate();
        subordinates.stream().forEach(employee -> System.out.println(employee.getName())); // Крокодилова Людмила Петровна, Сидоров Василий Васильевич, Зайцев Валерий Петрович
    }
}
