package io.ylab.intensive.lesson04.filesort;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import javax.xml.crypto.Data;

import io.ylab.intensive.lesson04.DbUtil;

public class FileSorterTest {
    public static void main(String[] args) throws SQLException, IOException {
        DataSource dataSource = initDb();
        File data = new File("src/main/resources/lessons/lesson4/data.txt");
        FileSorter fileSorter = new FileSortImpl(dataSource);
        File res = fileSorter.sort(data);

    }

    public static DataSource initDb() throws SQLException {
        String createSortTable = ""
                + "drop table if exists numbers;"
                + "CREATE TABLE if not exists numbers (\n"
                + "\tval bigint\n"
                + ");";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createSortTable, dataSource);
        return dataSource;
    }
}
