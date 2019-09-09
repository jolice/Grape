package me.riguron.grape.inject;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BeanInjection {

    private final FieldInjection fieldInjection;
    private final SetterInjection setterInjection;

    public void inject(Object bean) {
        fieldInjection.inject(bean);
        setterInjection.inject(bean);
    }
}
