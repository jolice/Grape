package me.riguron.grape.bean.matcher.policy;

import lombok.RequiredArgsConstructor;
import me.riguron.grape.exception.ExceptionProvider;
import me.riguron.grape.exception.dependency.UnsatisfiedDependencyException;

import javax.inject.Named;

@RequiredArgsConstructor
public class ProvidedNamedPolicy implements BindingPolicy {

    private final Named named;

    @Override
    public boolean isMandatory() {
        return true;
    }

    @Override
    public ExceptionProvider unsatisfiedError() {
        return () -> new UnsatisfiedDependencyException("No bean with name " + named.value());
    }
}
