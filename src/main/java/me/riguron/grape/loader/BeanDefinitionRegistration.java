package me.riguron.grape.loader;

import lombok.RequiredArgsConstructor;
import me.riguron.grape.bean.BeanDefinition;
import me.riguron.grape.bean.registry.Registry;

@RequiredArgsConstructor
public class BeanDefinitionRegistration {

    private final Registry<BeanDefinition> beanDefinitionRegistry;

    public void register(Class<?> type, BeanDefinition beanDefinition) {
        Class<?> targetType = type.getInterfaces().length > 0 ? type.getInterfaces()[0] : type;
        beanDefinitionRegistry.put(targetType, beanDefinition);
    }
}
