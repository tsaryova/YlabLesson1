package io.ylab.lessons.lesson3.validator;

import io.ylab.lessons.lesson3.validator.exceptions.WrongLoginException;

public class PasswordValidatorTest {
    public static void main(String[] args) throws WrongLoginException {
        String login = "djffhjfh_";
        Character[] pass = {'a', 'd', '_', '9'};
        Character[] passConfirm = {'a', 'd', '_', '9'};
        System.out.println(PasswordValidator.validate(login, pass, passConfirm)); //true

        System.out.println();
        String wrongLogin = "djffhjfh_++";
        //Логин содержит недопустимые символы
        System.out.println(PasswordValidator.validate(wrongLogin, pass, passConfirm)); //false

        System.out.println();
        //Пароль слишком длинный
        Character[] bigPass = {'a', 'd', '_', '9', 'a', 'd', '_', '9', 'a', 'd', '_', '9', 'a', 'd', '_', '9', 'a', 'd', '_', '9'};
        System.out.println(PasswordValidator.validate(login, bigPass, passConfirm)); //false

        System.out.println();
        Character[] wrongPass = {'a', 'd', '_', '9', '+'};
        //Пароль содержит недопустимые символы
        System.out.println(PasswordValidator.validate(login, wrongPass, passConfirm)); //false

        System.out.println();
        Character[] wrongConfirmPass = {'a', 'd', '_', '9', 'a'};
        //Пароль и подтверждение не совпадают
        System.out.println(PasswordValidator.validate(login, pass, wrongConfirmPass)); //false
    }

}
