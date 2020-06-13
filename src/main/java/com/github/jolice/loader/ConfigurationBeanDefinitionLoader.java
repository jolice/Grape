package com.github.jolice.loader;

import com.github.jolice.bean.Configuration;
import com.github.jolice.provider.MethodProvider;
import com.github.jolice.reflection.MethodInvoker;
import lombok.AllArgsConstructor;
import com.github.jolice.bean.BeanDefinition;

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
