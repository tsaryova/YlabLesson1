package io.ylab.intensive.lesson05.messagefilter.filterword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

@Component
public class FilterWordDaoImpl implements FilterWordDao {

    private static final String TABLE_NAME = "filter_word";

    private DataSource dataSource;

    @Autowired
    public FilterWordDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void saveBatchWords(List<String> wordList) throws SQLException {
        String insertSQL = "INSERT INTO " + TABLE_NAME + "(value) VALUES(?)";
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            for (String word : wordList) {
                preparedStatement.setString(1, word.toLowerCase(Locale.ROOT));
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        }
    }

    @Override
    public FilterWord findWord(String valueWord) throws SQLException {
        String findPersonSQL = "SELECT * FROM " + TABLE_NAME + " WHERE value ILIKE ?";
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findPersonSQL)) {

            preparedStatement.setString(1, valueWord);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                FilterWord filterWord = new FilterWord();
                filterWord.setId(resultSet.getLong("id"));
                filterWord.setValue(resultSet.getString("value"));

                return filterWord;
            }
        }

        return null;
    }

}
