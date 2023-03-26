package io.ylab.intensive.lesson04.persistentmap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 * Класс, методы которого надо реализовать
 */
public class PersistentMapImpl implements PersistentMap {

    private DataSource dataSource;
    private String name;

    public PersistentMapImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.name = "";
    }

    @Override

    public void init(String name) {
        if (name != null) {
            this.name = name;
        } else {
            this.name = "";
            System.out.println("Передано null для инициализации Map. Выполнена инициализация Map экземпляра c пустым названием");
        }
    }

    @Override
    public boolean containsKey(String key) throws SQLException {
        if (this.get(key) != null) {
            return true;
        }

        return false;
    }

    @Override
    public List<String> getKeys() throws SQLException {
        List<String> listKeys = new ArrayList<>();

        String getKeysSql = "SELECT key FROM persistent_map WHERE map_name=? AND value IS NOT NULL";
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getKeysSql)) {

            preparedStatement.setString(1, this.name);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listKeys.add(resultSet.getString("key"));
            }
        }

        return listKeys;
    }

    @Override
    public String get(String key) throws SQLException {

        String getSql = "SELECT value FROM persistent_map WHERE map_name=? AND " + addPartSqlStrForKeyInWhere(key);
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getSql)) {

            preparedStatement.setString(1, this.name);
            if (key != null) {
                preparedStatement.setString(2, key);
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("value");
            }
        }

        return null;
    }

    @Override
    public void remove(String key) throws SQLException {
        String removeSql = "DELETE FROM persistent_map WHERE map_name=? AND " + addPartSqlStrForKeyInWhere(key);
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(removeSql)) {

            preparedStatement.setString(1, this.name);
            if (key != null){
                preparedStatement.setString(2, key);
            }

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void put(String key, String value) throws SQLException {
        if (this.get(key) != null) {
            this.remove(key);
        }

        String putSql = "INSERT INTO persistent_map VALUES(?, ?, ?)";
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(putSql)) {

            preparedStatement.setString(1, this.name);
            preparedStatement.setString(2, key);
            preparedStatement.setString(3, value);

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void clear() throws SQLException {
        String removeSql = "DELETE FROM persistent_map WHERE map_name=?";
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(removeSql)) {

            preparedStatement.setString(1, this.name);

            preparedStatement.executeUpdate();
        }
    }

    private String addPartSqlStrForKeyInWhere(String key) {
        if (key == null) {
            return "key IS NULL";
        } else {
            return "key=?";
        }
    }
}
