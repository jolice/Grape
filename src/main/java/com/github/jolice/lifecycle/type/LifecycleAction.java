package com.github.jolice.lifecycle.type;

import java.lang.annotation.Annotation;

public interface LifecycleAction {

    Class<? extends Annotation> getAnnotation();

}
