package com.github.jolice.loader;

import com.github.jolice.bean.BeanMeta;
import com.github.jolice.bean.registry.Registry;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BeanRegistration<T extends BeanMeta> {

    private final Registry<T> beanDefinitionRegistry;

    public void register(Class<?> type, T beanData) {
        Class<?> targetType = type.getInterfaces().length > 0 ? type.getInterfaces()[0] : type;
        beanDefinitionRegistry.put(targetType, beanData);
    }
}
