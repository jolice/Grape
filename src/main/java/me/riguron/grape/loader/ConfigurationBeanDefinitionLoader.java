package me.riguron.grape.loader;

import lombok.AllArgsConstructor;
import me.riguron.grape.bean.BeanDefinition;
import me.riguron.grape.bean.Configuration;
import me.riguron.grape.provider.MethodProvider;
import me.riguron.grape.reflection.MethodInvoker;

import javax.enterprise.inject.Produces;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

@AllArgsConstructor
public class ConfigurationBeanDefinitionLoader implements BeanDefinitionLoader {

    private final MethodInvoker methodInvoker;
    private final Set<Configuration> configurations;
    private final BeanDefinitionRegistration beanDefinitionRegistration;

    @Override
    public void load() {
        for (Configuration object : configurations) {
            for (Method x : object.getClass().getDeclaredMethods()) {
                if (Modifier.isPublic(x.getModifiers())) {
                    if (x.isAnnotationPresent(Produces.class)) {
                        if (!x.getReturnType().equals(void.class)) {
                            beanDefinitionRegistration.register(x.getReturnType(), new BeanDefinition(
                                    x.getReturnType(), new MethodProvider(
                                    x, object, methodInvoker), x));
                        }
                    }
                }
            }
        }

    }
}
