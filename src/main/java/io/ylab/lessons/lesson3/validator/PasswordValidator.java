package io.ylab.lessons.lesson3.validator;

import io.ylab.lessons.lesson3.validator.exceptions.WrongLoginException;
import io.ylab.lessons.lesson3.validator.exceptions.WrongPasswordException;

import java.util.Arrays;
import java.util.Locale;

public class PasswordValidator {

    private static final int MAX_COUNT_LOGIN_SYMBOLS = 19;
    private static final int MAX_COUNT_PASS_SYMBOLS = 19;
    private static final String LOGIN_VALID_SYMBOLS = "abcdefghijklmnopqrstuvwxyz0123456789_";
    private static final String PASSWORD_VALID_SYMBOLS = "abcdefghijklmnopqrstuvwxyz0123456789_";

    public static boolean validate(String login, Character[] password, Character[] confirmPassword) {
        try {
            checkLogin(login);
            checkPassword(password);
            checkConfirmPassword(password, confirmPassword);

        } catch (WrongLoginException wrongLoginException) {
            System.out.println(wrongLoginException.getMessage());

            return false;

        } catch (WrongPasswordException wrongPasswordException) {
            System.out.println(wrongPasswordException.getMessage());

            return false;
        }

        return true;

    }

    private static void checkLogin(String login) throws WrongLoginException {
        checkSymbolsLogin(login);
        checkLengthLogin(login);
    }

    private static void checkSymbolsLogin(String login) throws WrongLoginException {
        boolean isSymbolsValid = login.chars()
                .mapToObj(c -> (char) c)
                .allMatch(c -> LOGIN_VALID_SYMBOLS.contains(String.valueOf(c).toLowerCase(Locale.ROOT)));
        if (!isSymbolsValid) {
            throw new WrongLoginException("Логин содержит недопустимые символы");
        }
    }

    private static void checkLengthLogin(String login) throws WrongLoginException {
        if (login.isEmpty()) {
            throw new WrongLoginException("Логин отсутствует");
        }

        if (login.length() > MAX_COUNT_LOGIN_SYMBOLS) {
            throw new WrongLoginException("Логин слишком длинный");
        }
    }

    private static void checkPassword(Character[] password) throws WrongPasswordException {
        checkSymbolsPassword(password);
        checkLengthPassword(password);
    }

    private static void checkSymbolsPassword(Character[] password) throws WrongPasswordException {
        boolean isSymbolsValid = Arrays.stream(password)
                .allMatch(c -> PASSWORD_VALID_SYMBOLS.contains(String.valueOf(c).toLowerCase(Locale.ROOT)));
        if (!isSymbolsValid) {
            throw new WrongPasswordException("Пароль содержит недопустимые символы");
        }
    }

    private static void checkLengthPassword(Character[] password) throws WrongPasswordException {
        if (password.length == 0) {
            throw new WrongPasswordException("Пароль не введён");
        }

        if (password.length > MAX_COUNT_PASS_SYMBOLS) {
            throw new WrongPasswordException("Пароль слишком длинный");
        }
    }

    private static void checkConfirmPassword(Character[] password, Character[] confirmPassword)
            throws WrongPasswordException {

        if (!Arrays.equals(password, confirmPassword)) {
            throw new WrongPasswordException("Пароль и подтверждение не совпадают");
        }
    }

}
