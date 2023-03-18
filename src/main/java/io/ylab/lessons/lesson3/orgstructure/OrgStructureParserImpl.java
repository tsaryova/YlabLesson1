package io.ylab.lessons.lesson3.orgstructure;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class OrgStructureParserImpl implements OrgStructureParser {

    private List<Employee> employees;
    private static final int COUNT_LINES_TO_SKIP = 1;

    public OrgStructureParserImpl() {
        employees = new ArrayList<>();
    }


    @Override
    public Employee parseStructure(File csvFile) throws IOException {
        Employee boss = new Employee();
        try (FileReader filereader = new FileReader(csvFile);
             CSVReader csvReader = new CSVReaderBuilder(filereader)
                     .withSkipLines(COUNT_LINES_TO_SKIP)
                     .build();
        ) {

            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                String[] dataEmployee = nextLine[0].split(";");
                Employee employee = new Employee();
                employee.setId(Long.valueOf(dataEmployee[0]));
                if (!dataEmployee[1].isEmpty()) {
                    employee.setBossId(Long.valueOf(dataEmployee[1]));
                }

                employee.setName(dataEmployee[2]);
                employee.setPosition(dataEmployee[3]);
                employees.add(employee);
            }
            if (employees.size() > 0) {
                boss = employees.stream()
                        .filter(e -> e.getBossId() == null).findFirst().orElseThrow(Exception::new);

                for (Employee currEmployee : employees) {
                    List<Employee> listSubordinate = employees.stream()
                            .filter(e -> e.getBossId() == currEmployee.getId())
                            .collect(Collectors.toList());
                    if (listSubordinate.size() > 0) {
                        currEmployee.getSubordinate().addAll(listSubordinate);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return boss;
    }
}
