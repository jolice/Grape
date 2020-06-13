package com.github.jolice.bean.matcher.policy;

import com.github.jolice.exception.ExceptionProvider;
import lombok.RequiredArgsConstructor;
import com.github.jolice.exception.dependency.UnsatisfiedDependencyException;

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
