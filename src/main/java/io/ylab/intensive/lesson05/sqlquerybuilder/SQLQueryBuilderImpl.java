package io.ylab.intensive.lesson05.sqlquerybuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    /**
     * Typical types are "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM"
     * if null types include all typical types.
     */
    private static final String[] TABLE_TYPES = null;

    private DataSource dataSource;
    private static Logger logger = LoggerFactory.getLogger(SQLQueryBuilderImpl.class);


    @Autowired
    public SQLQueryBuilderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String queryForTable(String tableName) throws SQLException {
        String result = null;

        if (!getExistTableNameList(tableName).isEmpty()) {
            List<String> columnNameList = getColumnNameList(tableName);
            if (columnNameList.isEmpty()) {
                logger.info(String.format("Table %s hasn't columns", tableName));
            } else {
                result = "SELECT " + String.join(", ", columnNameList) + " FROM " + tableName;
            }
        }

        return result;
    }

    @Override
    public List<String> getTables() throws SQLException {
        return getExistTableNameList("%");
    }

    private List<String> getExistTableNameList(String tableName) throws SQLException {
        List<String> tableNames = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, tableName, TABLE_TYPES);
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
