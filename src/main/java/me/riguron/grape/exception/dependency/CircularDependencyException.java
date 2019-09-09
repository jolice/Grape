package me.riguron.grape.exception.dependency;

public class CircularDependencyException extends RuntimeException {

    public CircularDependencyException(Object item) {
        super("Circular dependency involving " + item);
    }
}
