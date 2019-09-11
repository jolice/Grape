package me.riguron.grape.bean.matcher.policy;

import lombok.RequiredArgsConstructor;
import me.riguron.grape.exception.ExceptionProvider;

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
        return () -> new IllegalStateException("No bean with name " + target.getAnnotation(Named.class).value());
    }
}
