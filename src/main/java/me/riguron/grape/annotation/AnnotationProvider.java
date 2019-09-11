package me.riguron.grape.annotation;

import java.lang.annotation.Annotation;

public interface AnnotationProvider {

    <T extends Annotation> T getAnnotation(Class<T> annotationClass);

}
