package me.riguron.grape.dependency;

import lombok.Value;

import java.lang.reflect.AnnotatedElement;

@Value
public class Dependency {

    private Class<?> type;
    private AnnotatedElement annotatedElement;

}
