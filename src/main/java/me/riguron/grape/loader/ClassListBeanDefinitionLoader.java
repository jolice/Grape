package me.riguron.grape.loader;

import lombok.AllArgsConstructor;
import me.riguron.grape.bean.BeanDefinition;
import me.riguron.grape.provider.ConstructorProvider;
import me.riguron.grape.reflection.ConstructorLookup;

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
                        .orElseThrow(() -> new IllegalStateException("No constructor present for the injection: " + beanType)));

    }
}
