package com.github.jolice.lifecycle.type;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;

public class PostConstructAction implements LifecycleAction {
    @Override
    public Class<? extends Annotation> getAnnotation() {
        return PostConstruct.class;
    }
}
