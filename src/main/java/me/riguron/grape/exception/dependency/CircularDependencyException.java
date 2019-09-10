package me.riguron.grape.exception.dependency;

public class CircularDependencyException extends RuntimeException {

    public CircularDependencyException(Object from, Object to) {
        super("Circular dependency: " + (from + " > " + to));
    }
}
