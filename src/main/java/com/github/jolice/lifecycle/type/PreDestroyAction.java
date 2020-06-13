package com.github.jolice.lifecycle.type;

import javax.annotation.PreDestroy;
import java.lang.annotation.Annotation;

public class PreDestroyAction implements LifecycleAction {
    @Override
    public Class<? extends Annotation> getAnnotation() {
        return PreDestroy.class;
    }
}
