package io.ylab.intensive.lesson04.persistentmap;

import java.sql.SQLException;
import javax.sql.DataSource;

import io.ylab.intensive.lesson04.DbUtil;

public class PersistenceMapTest {
  public static void main(String[] args) throws SQLException {
    DataSource dataSource = initDb();
    PersistentMap persistentMap = new PersistentMapImpl(dataSource);
    // Написать код демонстрации работы

    persistentMap.init("map1");
    persistentMap.put("key10", "val1");
    persistentMap.put("key2", "val2");
    persistentMap.put("key30", "");

    persistentMap.init("map2");
    persistentMap.put("key1", "val1");
    persistentMap.put("key2", "val2");
    System.out.println(persistentMap.get("key2")); //val2

    persistentMap.put("key3", null);
    persistentMap.put("key2", "val222222");
    persistentMap.put("", "val222222");

    System.out.println(persistentMap.get("key2")); //val222222

    System.out.println(persistentMap.containsKey("key2")); //true
    System.out.println(persistentMap.containsKey("key4")); //false
    System.out.println(persistentMap.containsKey("")); //true


    System.out.println(persistentMap.getKeys()); //key1, key2, ""

    persistentMap.init("map1");
    System.out.println(persistentMap.getKeys()); //key10, key2, key30
    System.out.println(persistentMap.get("key10")); //val1
    System.out.println(persistentMap.get("key")); //null
    System.out.println(persistentMap.get("key30")); //""
    persistentMap.clear();
    System.out.println(persistentMap.getKeys()); //[]

    persistentMap.init(null); //Передано null для инициализации Map. Выполнена инициализация Map экземпляра c пустым названием
    persistentMap.put("key10", "val1");
    persistentMap.put("key2", "val2");
    persistentMap.put("key30", "");

    System.out.println(persistentMap.getKeys()); //key10, key2, key30
  }
  
  public static DataSource initDb() throws SQLException {
    String createMapTable = "" 
                                + "drop table if exists persistent_map; " 
                                + "CREATE TABLE if not exists persistent_map (\n"
                                + "   map_name varchar,\n"
                                + "   KEY varchar,\n"
                                + "   value varchar\n"
                                + ");";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(createMapTable, dataSource);
    return dataSource;
  }
}
