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
            for (Method method : object.getClass().getDeclaredMethods()) {
                if (Modifier.isPublic(method.getModifiers())) {
                    if (method.isAnnotationPresent(Produces.class)) {
                        if (!method.getReturnType().equals(void.class)) {
                            result.add(new BeanDefinition(
                                    method.getReturnType(), new MethodProvider(
                                    method, object, methodInvoker), method));
                        }
                    }
                }
            }
        }
        return result;
    }
}
