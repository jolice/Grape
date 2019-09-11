package me.riguron.grape.bean.lookup;

import me.riguron.grape.annotation.Name;
import me.riguron.grape.bean.matcher.BeanMatcher;
import me.riguron.grape.bean.matcher.NamedMatcher;
import me.riguron.grape.bean.matcher.policy.BindingPolicy;
import me.riguron.grape.bean.matcher.policy.ProvidedNamedPolicy;

import javax.inject.Named;

public class NamedLookup implements LookupParams {

    private final Named named;

    public NamedLookup(String beanName) {
        this.named = new Name(beanName);
    }

    @Override
    public BeanMatcher beanMatcher() {
        return new NamedMatcher(named);
    }

    @Override
    public BindingPolicy bindingPolicy() {
        return new ProvidedNamedPolicy(named);
    }
}
