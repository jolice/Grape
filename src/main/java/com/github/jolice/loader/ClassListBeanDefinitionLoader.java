package com.github.jolice.loader;

import com.github.jolice.provider.ConstructorProvider;
import lombok.AllArgsConstructor;
import com.github.jolice.bean.BeanDefinition;
import com.github.jolice.reflection.ConstructorLookup;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ClassListBeanDefinitionLoader implements BeanDefinitionLoader {

    private ConstructorLookup constructorLookup;
    private Set<Class<?>> beans;

    @Override
    public List<BeanDefinition> load() {
        return beans
                .stream()
                .map(this::findConstructor)
                .map(x -> new BeanDefinition(x.getDeclaringClass(), new ConstructorProvider(x), x.getDeclaringClass()))
                .collect(Collectors.toList());
    }

    private Constructor<?> findConstructor(Class<?> beanType) {
        final Optional<Constructor<?>> injectConstructor = constructorLookup.getInjectConstructor(beanType);
        return injectConstructor
                .orElseGet(() -> constructorLookup.getEmptyConstructor(beanType)
                        .orElseThrow(() -> noConstructorError(beanType)));
    }


    private RuntimeException noConstructorError(Class<?> beanType) {
        return new IllegalStateException("No constructor present for the injection: " + beanType + ". Make sure that either public empty constructor or " +
                "constructor annotated with @Inject exists and check the access privileges of the class. Also, non-static inner classes are not supported for the injection.");


    }
}
