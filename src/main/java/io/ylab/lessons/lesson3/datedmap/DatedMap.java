package io.ylab.lessons.lesson3.datedmap;

import java.util.Date;
import java.util.Set;

public interface DatedMap {

    /**
     * Помещает в map пару ключ и значение
     *
     * @param key
     * @param value
     */
    void put(String key, String value);

    /**
     * Получает значение, связанное с переданным в метод ключом
     *
     * @param key
     * @return Возвращает значение, связанное с переданным в метод ключом
     */
    String get(String key);

    /**
     * Метод, проверяющий, есть ли в map значение для данного ключа
     *
     * @param key
     * @return true или false в зависимости от результата сравнения
     */
    boolean containsKey(String key);

    /**
     * Получая на вход ключ, удаляет из map ключ и значение, с ним связанное
     *
     * @param key
     */
    void remove(String key);

    /**
     * Возвращает множество ключей, присутствующее в map
     *
     * @return множество Set ключей в map
     */
    Set<String> keySet();

    /**
     * Получая на вход ключ, проверяет, что для данного ключа
     * существует значение в map.
     *
     * @param key
     * @return При существовании значения в map возвращает дату, когда оно было добавлено
     * или null в противном случае
     */
    Date getKeyLastInsertionDate(String key);

}
