package me.riguron.grape.bean.lookup;

import lombok.RequiredArgsConstructor;
import me.riguron.grape.bean.BeanMeta;
import me.riguron.grape.bean.matcher.BeanMatcher;
import me.riguron.grape.bean.matcher.CompoundMatcher;
import me.riguron.grape.bean.matcher.ExactClassMatcher;
import me.riguron.grape.bean.matcher.policy.BindingPolicy;
import me.riguron.grape.bean.registry.BeanQuery;
import me.riguron.grape.bean.registry.Registry;

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
