package me.riguron.grape.exception.dependency;

public class AmbiguousDependencyException extends RuntimeException {

    public AmbiguousDependencyException(String message) {
        super(message);
    }
}
