package com.github.jolice.bean.lookup;

import com.github.jolice.bean.BeanMeta;
import com.github.jolice.bean.matcher.BeanMatcher;
import com.github.jolice.bean.matcher.CompoundMatcher;
import com.github.jolice.bean.matcher.ExactClassMatcher;
import com.github.jolice.bean.matcher.policy.BindingPolicy;
import com.github.jolice.bean.registry.BeanQuery;
import com.github.jolice.bean.registry.Registry;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class BeanLookup<T extends BeanMeta> {

    private final Registry<T> registry;

    public T lookup(Class<?> type, Class<?> requestSource, LookupParams lookupParams) {

        Class<?> finalType = getFinalType(type);
        BeanMatcher beanMatcher = createMatcher(type, finalType, lookupParams.beanMatcher());
        BindingPolicy bindingPolicy = lookupParams.bindingPolicy();

        BeanQuery beanQuery;
        if (void.class.equals(requestSource)) {
            beanQuery = new BeanQuery(finalType, beanMatcher, bindingPolicy);
        } else {
            beanQuery = new BeanQuery(finalType, requestSource, beanMatcher, bindingPolicy);
        }

        return registry.get(beanQuery);
    }

    private BeanMatcher createMatcher(Class<?> type, Class<?> finalType, BeanMatcher beanMatcher) {
        if (!finalType.equals(type)) {
            return new CompoundMatcher(new ExactClassMatcher(type), beanMatcher);
        }
        return beanMatcher;
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
