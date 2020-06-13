package com.github.jolice.exception.dependency;

public class AmbiguousDependencyException extends RuntimeException {

    public AmbiguousDependencyException(String message) {
        super(message);
    }
}
