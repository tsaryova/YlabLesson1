package io.ylab.intensive.lesson05.sqlquerybuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SQLQueryBuilderImpl implements SQLQueryBuilder {

    private DataSource dataSource;

    @Autowired
    public SQLQueryBuilderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String queryForTable(String tableName) throws SQLException {
        if (!getExistTableNameList(tableName).isEmpty()) {
            List<String> columnNameList = getColumnNameList(tableName);

            return "SELECT " + String.join(", ", columnNameList) + " FROM " + tableName;
        }

        return null;
    }

    @Override
    public List<String> getTables() throws SQLException {
        return getExistTableNameList("%");
    }

    private List<String> getExistTableNameList(String tableName) throws SQLException {
        List<String> tableNames = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet resultSet = metaData.getTables(null, null, tableName, types);
            while (resultSet.next()) {
                tableNames.add(resultSet.getString("TABLE_NAME"));
            }
            resultSet.close();
        }

        return tableNames;
    }

    private List<String> getColumnNameList(String tableName) throws SQLException {
        List<String> columnNameList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, tableName, "%");
            while (resultSet.next()) {
                columnNameList.add(resultSet.getString("COLUMN_NAME"));
            }
            resultSet.close();
        }

        return columnNameList;
    }
}
