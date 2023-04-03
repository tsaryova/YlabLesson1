package io.ylab.intensive.lesson05.messagefilter.filterword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class WordFilterDbUtil {

    private static final String NAME_TABLE_FILTER_WORLD = "filter_word";

    private DataSource dataSource;

    @Autowired
    public WordFilterDbUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void createTable() throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            if (!tableExists(connection)) {
                connection.createStatement().executeUpdate("create table " +
                        NAME_TABLE_FILTER_WORLD +
                        " (id serial primary key, value VARCHAR(255))");
            }
        }
    }

    public void clearTable() throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            if (!tableExists(connection)) {
                connection.createStatement().executeUpdate("delete from " + NAME_TABLE_FILTER_WORLD);
            }
        }
    }

    private boolean tableExists(Connection connection) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, NAME_TABLE_FILTER_WORLD, new String[]{"TABLE"});

        return resultSet.next();
    }

}
