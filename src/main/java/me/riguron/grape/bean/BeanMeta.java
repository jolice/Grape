package me.riguron.grape.bean;

import java.lang.reflect.AnnotatedElement;

public interface BeanMeta {

    AnnotatedElement getAnnotationData();

    Class<?> getBeanClass();

}
