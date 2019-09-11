package me.riguron.grape.bean;

import me.riguron.grape.annotation.Primary;

import java.lang.reflect.AnnotatedElement;

public interface BeanMeta {

    AnnotatedElement getAnnotationData();

    Class<?> getBeanClass();

    default boolean isPrimary() {
        return getAnnotationData().isAnnotationPresent(Primary.class);
    }
}
