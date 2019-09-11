package me.riguron.grape.loader;

import lombok.RequiredArgsConstructor;
import me.riguron.grape.bean.BeanMeta;
import me.riguron.grape.bean.registry.Registry;

@RequiredArgsConstructor
public class BeanRegistration<T extends BeanMeta> {

    private final Registry<T> beanDefinitionRegistry;

    public void register(Class<?> type, T beanData) {
        Class<?> targetType = type.getInterfaces().length > 0 ? type.getInterfaces()[0] : type;
        beanDefinitionRegistry.put(targetType, beanData);
    }
}
