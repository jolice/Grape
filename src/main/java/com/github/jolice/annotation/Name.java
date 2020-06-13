package com.github.jolice.annotation;

import javax.inject.Named;
import java.lang.annotation.Annotation;

/**
 * Allows clients to specify the name of the bean to be obtained from the container.
 */
public class Name implements Named {

    private final String value;

    public Name(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return Name.class;
    }
}
