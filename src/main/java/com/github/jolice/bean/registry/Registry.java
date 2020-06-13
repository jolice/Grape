package com.github.jolice.bean.registry;

import com.github.jolice.bean.BeanCollection;
import com.github.jolice.bean.BeanMeta;
import com.github.jolice.exception.dependency.AmbiguousDependencyException;
import com.github.jolice.exception.dependency.UnsatisfiedDependencyException;
import com.github.jolice.exception.injection.InjectionPointError;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Registry<T extends BeanMeta> {

    private Map<Class<?>, BeanCollection<T>> data = new ConcurrentHashMap<>();

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
        List<T> result = items.stream().filter(beanQuery.getBeanMatcher()::matches).collect(Collectors.toList());
        if (result.size() == 1) {
            return result.get(0);
        } else {
            if (result.isEmpty()) {
                if (beanQuery.getBindingPolicy().isMandatory()) {
                    return fail(beanQuery, beanQuery.getBindingPolicy().unsatisfiedError().exception());
                } else {
                    return items.size() > 1 ? findPrimary(items, beanQuery) : items.get(0);
                }
            } else {
                return findPrimary(result, beanQuery);
            }
        }
    }

    private T findPrimary(List<T> result, BeanQuery beanQuery) {
        return result.
                stream()
                .filter(BeanMeta::isPrimary)
                .findFirst()
                .orElseGet(() -> fail(beanQuery, new AmbiguousDependencyException("Multiple beans of type " + beanQuery.getType() + " found, which one is undefined")));
    }


    private T handleNoData(BeanQuery beanQuery) {
        return fail(beanQuery,  beanQuery.getBindingPolicy().isMandatory() ?
                beanQuery.getBindingPolicy().unsatisfiedError().exception() :
                new UnsatisfiedDependencyException("No beans of type " + beanQuery.getType() + " found"));
    }

    private <R> R fail(BeanQuery beanQuery, RuntimeException cause) {
        throw beanQuery.getQuerySource() != null ? new InjectionPointError(beanQuery.getQuerySource(), cause) : cause;
    }

    public Collection<T> getAll() {
        return data.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }
}
