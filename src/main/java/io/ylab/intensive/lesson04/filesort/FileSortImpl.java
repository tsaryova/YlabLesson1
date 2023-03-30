package io.ylab.intensive.lesson04.filesort;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.sql.DataSource;

public class FileSortImpl implements FileSorter {
    private DataSource dataSource;
    private List<Long> longListFromFile;
    private List<Long> sortedLongList;
    private static final String SORTED_FILE_PATH_NAME = "src/main/resources/lessons/lesson4/sorted_data.txt";

    public FileSortImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.longListFromFile = new ArrayList<>();
        this.sortedLongList = new ArrayList<>();
    }

    @Override
    public File sort(File data) throws IOException, SQLException {
        setLongListFromFile(data);

        long startWithBatch = System.currentTimeMillis();
        addDataToDBWithBatch();
        long endWithBatch = System.currentTimeMillis();
        System.out.println("Добавление с batch-processing : " + (endWithBatch - startWithBatch));

        setSortedLongListFromDB();
        File sortedFile = writeSortedNumbers();

        return sortedFile;
    }

    private void setLongListFromFile(File data) throws IOException {
        try (Scanner scanner = new Scanner(data)) {
            while (scanner.hasNextLong()) {
                this.longListFromFile.add(scanner.nextLong());
            }
        }
    }

    private void addDataToDBWithBatch() throws SQLException {
        String insertSQL = "INSERT INTO numbers VALUES(?)";
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            for (Long element : this.longListFromFile) {
                preparedStatement.setLong(1, element);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        }
    }

    private void addDataToDBWithoutBatch() throws SQLException {
        String insertSQL = "INSERT INTO numbers VALUES(?)";
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            for (Long element : this.longListFromFile) {
                preparedStatement.setLong(1, element);
                preparedStatement.executeUpdate();
            }
        }
    }

    private void setSortedLongListFromDB() throws SQLException {
        String selectSortSQL = "SELECT val FROM numbers ORDER BY val DESC";
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSortSQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                this.sortedLongList.add(resultSet.getLong("val"));
            }
        }
    }

    private File writeSortedNumbers() throws IOException {
        File sortedFile = new File(SORTED_FILE_PATH_NAME);

        try (PrintWriter pw = new PrintWriter(sortedFile)) {
            for (Long element : this.sortedLongList) {
                pw.println(element);
            }
            pw.flush();
        }

        return sortedFile;
    }

}
