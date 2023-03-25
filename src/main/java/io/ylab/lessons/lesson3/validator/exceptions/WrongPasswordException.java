package io.ylab.lessons.lesson3.validator.exceptions;

public class WrongPasswordException extends Exception {

    public WrongPasswordException() { }

    public WrongPasswordException(String message) {
        super(message);
    }

}
