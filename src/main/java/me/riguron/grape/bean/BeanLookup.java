package me.riguron.grape.bean;

import lombok.RequiredArgsConstructor;
import me.riguron.grape.bean.matcher.NamedMatcher;
import me.riguron.grape.bean.matcher.policy.NamedPolicy;
import me.riguron.grape.bean.registry.BeanQuery;
import me.riguron.grape.bean.registry.Registry;

import javax.inject.Named;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

@RequiredArgsConstructor
public class BeanLookup<T extends BeanMeta> {

    private final Registry<T> registry;

    public T lookup(Class<?> type, Class<?> requestSource, AnnotatedElement element) {
        Class<?> finalType = getFinalType(type);
        return registry.get(new BeanQuery(
                finalType, requestSource, new NamedMatcher(element.getAnnotation(Named.class)), new NamedPolicy(element)
        ));
    }

    private Class<?> getFinalType(Class<?> original) {
        Class<?> result = original;
        if (original.getInterfaces().length > 0) {
            result = original.getInterfaces()[0];
        }
        return result;
    }

    public Collection<T> getAll() {
        return registry.getAll();
    }
}
