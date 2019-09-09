package me.riguron.grape.bean.registry;

import me.riguron.grape.bean.BeanCollection;
import me.riguron.grape.bean.BeanMeta;
import me.riguron.grape.exception.dependency.AmbiguousDependencyException;
import me.riguron.grape.exception.dependency.UnsatisfiedDependencyException;
import me.riguron.grape.exception.injection.InjectionPointError;

import java.util.*;
import java.util.stream.Collectors;

public class Registry<T extends BeanMeta> {

    private Map<Class<?>, BeanCollection<T>> data = new HashMap<>();

    public void put(Class<?> type, T definition) {
        data.computeIfAbsent(type, aClass -> new BeanCollection<>()).put(definition);
    }

    public T get(BeanQuery beanQuery) {
        List<T> items = data.get(beanQuery.getType());
        if (items == null) {
            return handleNoData(beanQuery);
        } else {
            return handleCollection(beanQuery, items);
        }
    }

    private T handleCollection(BeanQuery beanQuery, List<T> items) {
        Optional<T> result = items.stream().filter(beanQuery.getBeanMatcher()::matches).findFirst();
        if (result.isPresent()) {
            return result.get();
        } else {
            if (beanQuery.getBindingPolicy().isMandatory()) {
                return fail(beanQuery, beanQuery.getBindingPolicy().unsatisfiedError().exception());
            } else {
                if (items.size() > 1) {
                    return fail(beanQuery, new AmbiguousDependencyException("Multiple beans of type " + beanQuery.getType() + " found, which one is undefined"));
                } else {
                    return items.get(0);
                }
            }
        }
    }

    private T handleNoData(BeanQuery beanQuery) {
        if (beanQuery.getBindingPolicy().isMandatory()) {
            return fail(beanQuery, beanQuery.getBindingPolicy().unsatisfiedError().exception());
        } else {
            return fail(beanQuery, new UnsatisfiedDependencyException("No beans of type " + beanQuery.getType() + " found"));
        }
    }


    private <R> R fail(BeanQuery beanQuery, RuntimeException cause) {
        if (beanQuery.getQuerySource() != null) {
            throw new InjectionPointError(beanQuery.getQuerySource(), cause);
        } else {
            throw cause;
        }
    }

    public Collection<T> getAll() {
        return data.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }
}
