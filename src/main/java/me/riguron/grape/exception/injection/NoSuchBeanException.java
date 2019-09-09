package me.riguron.grape.exception.injection;

public class NoSuchBeanException extends RuntimeException {

    public NoSuchBeanException(String message) {
        super(message);
    }
}
