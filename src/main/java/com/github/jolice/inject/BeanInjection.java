package com.github.jolice.inject;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BeanInjection {

    private final FieldInjection fieldInjection;
    private final MethodInjection methodInjection;

    public void inject(Object bean) {
        fieldInjection.inject(bean);
        methodInjection.inject(bean);
    }
}
