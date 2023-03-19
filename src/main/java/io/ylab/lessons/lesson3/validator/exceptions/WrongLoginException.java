package io.ylab.lessons.lesson3.validator.exceptions;

public class WrongLoginException extends Exception {

    public WrongLoginException() { }

    public WrongLoginException(String message) {
        super(message);
    }

}
