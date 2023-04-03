package io.ylab.intensive.lesson05.messagefilter.filterword;

import java.sql.SQLException;
import java.util.List;

public interface FilterWordDao {

    void saveBatchWords(List<String> wordList) throws SQLException;

    FilterWord findWord(String valueWord) throws SQLException;

}
