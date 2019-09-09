package me.riguron.grape.lifecycle.type;

import java.lang.annotation.Annotation;

public interface LifecycleAction {

    Class<? extends Annotation> getAnnotation();

}
