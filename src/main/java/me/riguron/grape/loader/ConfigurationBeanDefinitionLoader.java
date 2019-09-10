package me.riguron.grape.loader;

import lombok.AllArgsConstructor;
import me.riguron.grape.bean.BeanDefinition;
import me.riguron.grape.bean.Configuration;
import me.riguron.grape.provider.MethodProvider;
import me.riguron.grape.reflection.MethodInvoker;

import javax.enterprise.inject.Produces;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class ConfigurationBeanDefinitionLoader implements BeanDefinitionLoader {

    private final MethodInvoker methodInvoker;
    private final Set<Configuration> configurations;

    @Override
    public List<BeanDefinition> load() {
        List<BeanDefinition> result = new ArrayList<>();
        for (Configuration object : configurations) {
            for (Method x : object.getClass().getDeclaredMethods()) {
                if (Modifier.isPublic(x.getModifiers())) {
                    if (x.isAnnotationPresent(Produces.class)) {
                        if (!x.getReturnType().equals(void.class)) {
                            result.add(new BeanDefinition(
                                    x.getReturnType(), new MethodProvider(
                                    x, object, methodInvoker), x));
                        }
                    }
                }
            }
        }
        return result;
    }
}
