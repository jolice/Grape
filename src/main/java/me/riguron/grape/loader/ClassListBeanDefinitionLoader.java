package me.riguron.grape.loader;

import lombok.AllArgsConstructor;
import me.riguron.grape.bean.BeanDefinition;
import me.riguron.grape.provider.ConstructorProvider;
import me.riguron.grape.reflection.ConstructorLookup;

import java.lang.reflect.Constructor;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
public class ClassListBeanDefinitionLoader implements BeanDefinitionLoader {

    private BeanDefinitionRegistration beanDefinitionRegistration;
    private ConstructorLookup constructorLookup;
    private Set<Class<?>> beans;

    @Override
    public void load() {
        for (Class<?> bean : beans) {
            Optional<Constructor<?>> injectConstructor = constructorLookup.getInjectConstructor(bean);
            Constructor<?> constructor = injectConstructor
                    .orElseGet(() -> constructorLookup.getEmptyConstructor(bean)
                            .orElseThrow(() -> new IllegalStateException("No constructor present for the injection: " + bean)));



            beanDefinitionRegistration.register(bean, new BeanDefinition(bean, new ConstructorProvider(constructor), bean));
        }
    }
}
