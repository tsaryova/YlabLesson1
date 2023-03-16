package io.ylab.lessons.lesson2.snils;

public interface SnilsValidator {
    /**
     * Проверяет, что в строке содержится валидный номер СНИЛС
     *
     * @param snils номер снилс
     * @return результат проверки
     */
    boolean validate(String snils);
}
