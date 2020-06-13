package com.github.jolice.bean.matcher.policy;

import com.github.jolice.exception.ExceptionProvider;
import lombok.RequiredArgsConstructor;
import com.github.jolice.exception.dependency.UnsatisfiedDependencyException;

import javax.inject.Named;
import java.lang.reflect.AnnotatedElement;

/**
 * Defines a policy for the named beans. If injection point declares
 * the name of the bean to be injected, container looks for the bean
 * with the specified name, otherwise fails.
 */
@RequiredArgsConstructor
public class AnnotatedNamedPolicy implements BindingPolicy {

    private final AnnotatedElement target;


    @Override
    public boolean isMandatory() {
        return target.isAnnotationPresent(Named.class);
    }

    @Override
    public ExceptionProvider unsatisfiedError() {
        return () -> new UnsatisfiedDependencyException("No bean with name " + target.getAnnotation(Named.class).value());
    }
}
