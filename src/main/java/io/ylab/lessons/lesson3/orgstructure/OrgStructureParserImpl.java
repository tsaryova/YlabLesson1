package io.ylab.lessons.lesson3.orgstructure;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrgStructureParserImpl implements OrgStructureParser {

    private static final int COUNT_LINES_TO_SKIP = 1;

    private List<Employee> employees;
    private Employee mainBoss;

    public OrgStructureParserImpl() {
        employees = new ArrayList<>();
        mainBoss = new Employee();
    }

    @Override
    public Employee parseStructure(File csvFile) throws IOException {
        try (FileReader filereader = new FileReader(csvFile);
             CSVReader csvReader = new CSVReaderBuilder(filereader)
                     .withSkipLines(COUNT_LINES_TO_SKIP)
                     .build();
        ) {

            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                String[] dataEmployee = nextLine[0].split(";");
                addEmployee(dataEmployee);
            }

            if (employees.size() > 0) {
                setDataEmployees();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mainBoss;

    }

    private void addEmployee(String[] dataEmployee) {
        Employee employee = new Employee();

        employee.setId(Long.valueOf(dataEmployee[0]));
        if (!dataEmployee[1].isEmpty()) {
            employee.setBossId(Long.valueOf(dataEmployee[1]));
        }

        employee.setName(dataEmployee[2]);
        employee.setPosition(dataEmployee[3]);
        employees.add(employee);
    }

    private Employee getBossEmployee(Long bossId) {
        return employees.stream()
                .filter(e -> e.getId() == bossId)
                .findFirst()
                .orElse(null);
    }

    private List<Employee> getSubordinateEmployees(Employee employee) {
        return employees.stream()
                .filter(e -> e.getBossId() == employee.getId())
                .collect(Collectors.toList());
    }

    private void setDataEmployees() {
        for (Employee employee : employees) {
            Long bossId = employee.getBossId();
            if (bossId != null) {
                employee.setBoss(getBossEmployee(bossId));
            } else {
                mainBoss = employee;
            }

            employee.getSubordinate()
                    .addAll(getSubordinateEmployees(employee));
        }
    }

}
